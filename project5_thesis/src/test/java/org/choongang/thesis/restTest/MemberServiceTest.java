package org.choongang.thesis.restTest;

import org.choongang.global.Utils;
import org.choongang.thesisAdvance.services.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@ActiveProfiles("dev")
public class MemberServiceTest {
    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private MemberService memberService;

    @Autowired
    private Utils utils;

    @Test
    public void testGetEmailsByJob() {
        // Mock 데이터 설정
        String job = "developer";
        List<String> mockEmails = Arrays.asList("user1@example.com", "user2@example.com");

        // URL 구성
        String url = utils.url("/job", "member-service") + "?job=" + job;

        // Mock 설정
        Mockito.when(restTemplate.exchange(
                URI.create(url),
                HttpMethod.GET,
                Mockito.any(HttpEntity.class),
                Mockito.eq(new ParameterizedTypeReference<List<String>>() {}))
        ).thenReturn(new ResponseEntity<>(mockEmails, HttpStatus.OK));

        // 실제 호출
        List<String> emails = memberService.getEmailsByJob(job);

        // 검증
        Assertions.assertNotNull(emails);
        Assertions.assertEquals(2, emails.size());
        Assertions.assertTrue(emails.contains("user1@example.com"));
        Assertions.assertTrue(emails.contains("user2@example.com"));
    }
}
