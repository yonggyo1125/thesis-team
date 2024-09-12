package org.choongang.thesis.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.choongang.thesis.constants.ApprovalStatus;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.entities.Field;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.repositories.FieldRepository;
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
import java.util.List;
import java.util.Map;

@SpringBootTest
//@ActiveProfiles("dev")
public class ApiTest3 {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ThesisRepository thesisRepository;

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private ObjectMapper om;

    // XML을 JSON으로 변환하는 메서드
    public static String convertXmlToJson(String xml) {
        JSONObject jsonObject = XML.toJSONObject(xml);
        return jsonObject.toString(4); // 들여쓰기 포함
    }

    @Test
    @DisplayName("논문 데이터 DB 담기 테스트3, 필드 저장 테스트")
    void test1() throws Exception {

        String url = "https://open.kci.go.kr/po/openapi/openApiSearch.kci?apiCode=articleDetail&key=13281654&id=ART002358582";

        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class); //URL에 HTTP GET 요청을 보내고 응답 받아옴

        // xml을 json으로 변환
        String jsonResponse = convertXmlToJson(response.getBody());

        Map<String, Object> responseMap = om.readValue(jsonResponse, Map.class);

        // MetaData -> outputData -> record -> articleInfo에 접근
        Map<String, Object> metaData = (Map<String, Object>) responseMap.get("MetaData");
        Map<String, Object> outputData = (Map<String, Object>) metaData.get("outputData");
        Map<String, Object> record = (Map<String, Object>) outputData.get("record");

        // articleInfo 추출
        Map<String, Object> articleInfo = (Map<String, Object>) record.get("articleInfo");

        // 논문 제목 (title-group의 첫 번째 제목 사용)
        // title-group을 처리 (LinkedHashMap 형태)
        Map<String, Object> titleGroup = (Map<String, Object>) articleInfo.get("title-group");

        List<Map<String, Object>> articleTitles = (List<Map<String, Object>>) titleGroup.get("article-title");

        // 참고문헌
        Map<String, Object> referenceInfo = (Map<String, Object>) record.get("referenceInfo");
        List<Map<String, Object>> references = (List<Map<String, Object>>) referenceInfo.get("reference");

        // 각 레퍼런스를 문자열로 변환
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

        //키워드
        Map<String, Object> keywordGroup = (Map<String, Object>) articleInfo.get("keyword-group");
        List<String> keywordsList = (List<String>) keywordGroup.get("keyword");

        String keywords = String.join(", ", keywordsList);

        // 학문별 분류
        String _fields = (String) articleInfo.get("article-categories");
        String[] parts = _fields.split(" > ");
        String name = parts[0];
        String subtitle = parts.length > 1 ? parts[1] : "";

        Field field = fieldRepository.findBySubfield(subtitle);
        if (field.getSubfield().equals(subtitle)) {
            String _id = field.getId();
            System.out.println("_id: " + _id);
        } //field id 가져오기

        // 첫 번째 제목을 추출 (original, foreign, english 중 첫 번째 original 선택)
        String title = null;
        if (articleTitles != null && !articleTitles.isEmpty()) {
            title = (String) articleTitles.get(0).get("content");
        }

        // 작성자
        String poster = (String) ((Map<String, Object>) ((Map<String, Object>) articleInfo.get("author-group")).get("author")).get("name");

        // 초록 (abstract 내용 추출)
        String thAbstract = (String) ((Map<String, Object>) articleInfo.get("abstract")).get("content");

        // 발행기관 (journalInfo에서 publisher-name 추출)
        String publisher = (String) ((Map<String, Object>) record.get("journalInfo")).get("publisher-name");

        // 언어 추출
        String language = (String) articleInfo.get("article-language");

        // 기타 고정된 값 예시
        String country = "한국";
        Category category = Category.DOMESTIC;
        String userName = "user";  // 이 값을 동적으로 바꾸려면 로그인 정보를 참조
        String email = "user@test.org";  // 이 값도 동적으로 바꾸려면 로그인 정보를 참조

        // 논문 엔티티 생성
        Thesis thesis = Thesis.builder()
                .title(title)
                .category(category)
                .fields(List.of(field))
                .poster(poster)
                .thAbstract(thAbstract)
                .publisher(publisher)
                .reference(reference)
                .keywords(keywords)
                .language(language)
                .country(country)
                .userName(userName)
                .email(email)
                .visible(true)
                .approvalStatus(ApprovalStatus.APPROVED)
                .gid("ART002358582")  // 논문의 article-id를 그룹 ID로 사용
                .build();

        // 변환된 Thesis 객체를 데이터베이스에 저장

        thesisRepository.saveAndFlush(thesis);

        // 저장된 Thesis 확인을 위해 출력
        System.out.println("저장된 Thesis: " + thesis);
//        System.out.println("name" + name);
//        System.out.println("sub" + subtitle);
    }
}
