package org.choongang.global.tests;

import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Gender;
import org.choongang.member.constants.Job;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TestTokenService {
    private final ApiRequest apiRequest;

    public String getToken(Authority authority) {
        Map<String, Object> params = new HashMap<>();

        String email = "testuser_" + authority.name() + "@testuser.org";
        params.put("email", email);
        params.put("password", "_aA123456");
        params.put("confirmPassword", "_aA123456");
        params.put("userName", "testuser_" + authority.name());
        params.put("mobile", "01010001000");
        params.put("birth", "1999-12-31");
        params.put("job", Job.DOCTOR.name());
        params.put("gender", Gender.FEMALE.name());
        params.put("agree", "true");

        ApiRequest result = apiRequest.request("/account", "member-service", HttpMethod.POST, params);
        System.out.println(result.getResponse());

        return null;
    }
}
