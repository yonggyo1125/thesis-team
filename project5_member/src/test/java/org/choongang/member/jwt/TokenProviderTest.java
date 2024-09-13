package org.choongang.member.jwt;

import org.choongang.member.controllers.RequestJoin;
import org.choongang.member.services.MemberSaveService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

@SpringBootTest
@ActiveProfiles("Test")
public class TokenProviderTest {
    @Autowired
    private TokenProvider provider;

    @Autowired
    private MemberSaveService saveService;

    @BeforeEach
    void init() {
        RequestJoin form = new RequestJoin();
        form.setEmail("user01@test.org");
        form.setPassword("_aA123456");
        form.setConfirmPassword(form.getPassword());
        form.setMobile("010-1000-1000");
        form.setJob("PROFESSOR");
        form.setUserName("사용자01");
        form.setBirth(LocalDate.of(1990, 1, 1));
        form.setGender("MALE");
        form.setAgree(true);

        saveService.save(form);
    }

    @Test
    @DisplayName("토큰 발급 테스트")
    @WithMockUser(username = "user01@test.org", password = "_aA123456", authorities = "USER")
    void createTokenTest() {
        String token = provider.createToken("user01@test.org", "_aA123456");
        System.out.println(token);
    }
}
