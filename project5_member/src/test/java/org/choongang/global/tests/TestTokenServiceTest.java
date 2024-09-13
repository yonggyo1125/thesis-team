package org.choongang.global.tests;

import org.choongang.member.constants.Authority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestTokenServiceTest {
    @Autowired
    private TestTokenService tokenService;

    @Test
    void test1() {
        tokenService.getToken(Authority.USER);
    }
}
