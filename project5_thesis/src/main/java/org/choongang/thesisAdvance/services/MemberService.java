package org.choongang.thesisAdvance.services;


import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final ApiRequest apiRequest;

    // 직업으로 이메일 조회
    public List<String> getEmailsByJob(String job) {
        ApiRequest result = apiRequest.request("/job?job=" + job, "member-service");
        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toList(new TypeReference<>(){});
        }
        return null;
    }

    // 이메일로 직업 조회
    public List<String> getJobByEmails(String email) {
        ApiRequest result = apiRequest.request("/job?email=" + email, "member-service");
        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toList(new TypeReference<>(){});
        }
        return null;
    }

    /*
    private final RestTemplate restTemplate;
    private final Utils utils; // URL 생성 도구

    public List<String> getEmailsByJob(String job) {
        // 1. API 호출을 위한 URL 구성
        String url = utils.url("/api/members", "member-service") + "?job=" + job;

        // 2. 헤더 및 요청 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // 3. API 호출
        ResponseEntity<List<String>> response = restTemplate.exchange(
                URI.create(url),
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {});

        // 4. 응답으로부터 이메일 리스트 반환
        return response.getBody();
    }*/
}
