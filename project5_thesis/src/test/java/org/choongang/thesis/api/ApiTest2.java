package org.choongang.thesis.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.choongang.thesis.constants.ApprovalStatus;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.repositories.ThesisRepository;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class ApiTest2 {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ThesisRepository thesisRepository;

    @Autowired
    private ObjectMapper om;

    int userNum = 1;

    // XML을 JSON으로 변환하는 메서드
    public static String convertXmlToJson(String xml) {
        JSONObject jsonObject = XML.toJSONObject(xml);
        return jsonObject.toString(4); // 들여쓰기 포함
    }

    // article-id들을 추출하는 메서드
    public List<String> getArticleIds(String title) throws Exception {
//        String title = "사회과학";
        String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());

        // API URL 정의
        String url = "https://open.kci.go.kr/po/openapi/openApiSearch.kci?apiCode=referenceSearch&key=13281654&title=" + encodedTitle + "&displayCount=10";

        // API 호출해서 XML 응답 받기
        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class);

        // xml을 json으로 변환
        String jsonResponse = convertXmlToJson(response.getBody());

        // JSON을 Map으로 변환
        Map<String, Object> responseMap = om.readValue(jsonResponse, Map.class);

        // MetaData -> outputData -> record들에 접근
        Map<String, Object> metaData = (Map<String, Object>) responseMap.get("MetaData");
        Map<String, Object> outputData = (Map<String, Object>) metaData.get("outputData");

        // record가 List인지 확인 후 처리
        Object recordObj = outputData.get("record");

        // article-id들을 저장할 리스트 생성
        List<String> articleIds = new ArrayList<>();

        if (recordObj instanceof List) {
            // record가 List일 경우
            List<Map<String, Object>> records = (List<Map<String, Object>>) recordObj;

            // 각 record에서 article-id 추출
            for (Map<String, Object> record : records) {
                // article-id 추출
                String articleId = (String) record.get("article-id");

                // 추출한 article-id 리스트에 추가
                articleIds.add(articleId);
            }
        } else if (recordObj instanceof Map) {
            // record가 단일 객체일 경우
            Map<String, Object> record = (Map<String, Object>) recordObj;
            String articleId = (String) record.get("article-id");
            articleIds.add(articleId);
        }

        return articleIds;  // 추출된 article-id들을 리턴
    }

    @Test
    @DisplayName("논문 api 데이터")
    void saveThesesToDb() throws Exception {
        // 카테고리 리스트 정의
        List<String> categories = List.of(
                "공학 및 기술",
                "의학 및 보건학",
                "인문학",
                "사회과학",
                "농업 및 생명공학",
                "경영 및 경제",
                "예술 및 디자인"
        );

        for (String category : categories) {
            System.out.println("현재 카테고리:" + category);

            // article-id 리스트 가져오기 (카테고리 제목으로)
            List<String> articleIds = getArticleIds(category);

            // 각 article-id에 대해 논문 정보를 가져와서 DB에 저장
            for (String articleId : articleIds) {
                String url = "https://open.kci.go.kr/po/openapi/openApiSearch.kci?apiCode=articleDetail&key=13281654&id=" + articleId;

                ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class); // URL에 HTTP GET 요청을 보내고 응답 받아옴

                // xml을 json으로 변환
                String jsonResponse = convertXmlToJson(response.getBody());

                Map<String, Object> responseMap = om.readValue(jsonResponse, Map.class);

                // MetaData -> outputData -> record들에 접근
                Map<String, Object> metaData = (Map<String, Object>) responseMap.get("MetaData");
                Map<String, Object> outputData = (Map<String, Object>) metaData.get("outputData");

                // record가 List인지 확인 후 처리
                Object recordObj = outputData.get("record");

                if (recordObj instanceof List) {
                    List<Map<String, Object>> records = (List<Map<String, Object>>) recordObj;
                    for (Map<String, Object> record : records) {
                        saveThesis(record, articleId);  // 논문 데이터를 DB에 저장하는 메서드 호출
                    }
                } else if (recordObj instanceof Map) {
                    Map<String, Object> record = (Map<String, Object>) recordObj;
                    saveThesis(record, articleId);  // 논문 데이터를 DB에 저장하는 메서드 호출
                }
            }
        }
    }

    private void saveThesis(Map<String, Object> record, String articleId) throws Exception {
        // articleInfo 추출
        Map<String, Object> articleInfo = (Map<String, Object>) record.get("articleInfo");

        // 논문 제목 추출
        Map<String, Object> titleGroup = (Map<String, Object>) articleInfo.get("title-group");
        List<Map<String, Object>> articleTitles;

        // article-title이 List인지 확인
        Object articleTitleObj = titleGroup.get("article-title");
        if (articleTitleObj instanceof List) {
            articleTitles = (List<Map<String, Object>>) articleTitleObj;
        } else {
            // 단일 객체인 경우도 처리
            articleTitles = new ArrayList<>();
            articleTitles.add((Map<String, Object>) articleTitleObj);
        }

        // 참고문헌 추출
        Map<String, Object> referenceInfo = (Map<String, Object>) record.get("referenceInfo");
        List<Map<String, Object>> references;

        // reference가 List인지 확인
        Object referenceObj = referenceInfo.get("reference");
        if (referenceObj instanceof List) {
            references = (List<Map<String, Object>>) referenceObj;
        } else {
            references = new ArrayList<>();
            references.add((Map<String, Object>) referenceObj);
        }

        // 참고문헌 문자열로 변환
        StringBuilder referenceBuilder = new StringBuilder();
        int i = 1;
        for (Map<String, Object> reference : references) {
            String pubiYear = String.valueOf(reference.get("pubi-year"));
            String author = (String) reference.get("author");
            String title = (String) reference.get("title");
            String journalName = (String) reference.get("journal-name");
            String page = (String) reference.get("page");
            String doi = (String) reference.get("doi");

            // 각 레퍼런스를 포맷팅해서 추가
            referenceBuilder.append(String.format(i + ". %s (%s), \"%s\", %s, pp. %s, DOI: %s\n",
                    author, pubiYear, title, journalName, page, doi != null ? doi : "N/A"));
            i++;
        }
        String reference = referenceBuilder.toString();

        // 키워드 추출
        Map<String, Object> keywordGroup = (Map<String, Object>) articleInfo.get("keyword-group");
        List<String> keywordsList;

        // keyword가 List인지 확인
        Object keywordsObj = keywordGroup.get("keyword");
        if (keywordsObj instanceof List) {
            keywordsList = (List<String>) keywordsObj;
        } else {
            keywordsList = new ArrayList<>();
            keywordsList.add((String) keywordsObj);
        }

        String keywords = String.join(", ", keywordsList);

        // 첫 번째 제목을 추출
        String title = null;
        if (articleTitles != null && !articleTitles.isEmpty()) {
            title = (String) articleTitles.get(0).get("content");
        }

        // 작성자 추출
        String poster = null;
        Object authorGroupObj = articleInfo.get("author-group");
        if (authorGroupObj instanceof Map) {
            Map<String, Object> authorGroup = (Map<String, Object>) authorGroupObj;
            Object authorObj = authorGroup.get("author");

            if (authorObj instanceof Map) {
                poster = (String) ((Map<String, Object>) authorObj).get("name");
            } else if (authorObj instanceof List) {
                poster = (String) ((Map<String, Object>) ((List<?>) authorObj).get(0)).get("name");
            }
        }

        // 초록 추출
        String thAbstract = (String) ((Map<String, Object>) articleInfo.get("abstract")).get("content");

        // 발행기관 추출
        String publisher = (String) ((Map<String, Object>) record.get("journalInfo")).get("publisher-name");

        // 언어 추출
        String language = (String) articleInfo.get("article-language");

        // 승인 여부
        String verified = (String) articleInfo.get("verified");

        // 기타 고정된 값 예시
        String country = "한국";
        Category category = Category.DOMESTIC;
        String userName = "user" + userNum;  // 이 값을 동적으로 바꾸려면 로그인 정보를 참조
        String email = "user" + userNum + "@test.org";  // 이 값도 동적으로 바꾸려면 로그인 정보를 참조

        // 논문 엔티티 생성
        Thesis thesis = Thesis.builder()
                .title(title)
                .category(category)
                .poster(poster)
                .thAbstract(thAbstract)
                .publisher(publisher)
                .reference(reference)
                .keywords(keywords)
                .language(language)
                .visible(true)
                .country(country)
                .userName(userName)
                .email(email)
                .visible(true)
                .approvalStatus(ApprovalStatus.APPROVED)
                .gid(articleId)  // 논문의 article-id를 그룹 ID로 사용
                .build();

        // 변환된 Thesis 객체를 데이터베이스에 저장
        thesisRepository.saveAndFlush(thesis);
        userNum++;

        // 저장된 Thesis 확인을 위해 출력
        System.out.println("저장된 Thesis: " + thesis);
    }
}