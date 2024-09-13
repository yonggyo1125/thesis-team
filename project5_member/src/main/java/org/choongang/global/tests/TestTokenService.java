package org.choongang.global.tests;

import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Gender;
import org.choongang.member.constants.Job;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TestTokenService {
    private final ApiRequest apiRequest;

    public String getToken(Authority authority) {
        Map<String, Object> params = new HashMap<>();

        String email = "testuser_" + authority.name() + "" + System.currentTimeMillis()+ "@testuser.org";
        String password = "_aA123456";
        params.put("email", email);
        params.put("password", password);
        params.put("confirmPassword", password);
        params.put("userName", "testuser_" + authority.name());
        params.put("mobile", "01010001000");
        params.put("birth", "1999-12-31");
        params.put("job", Job.DOCTOR.name());
        params.put("gender", Gender.FEMALE.name());
        params.put("interests", List.of("관심분야"));
        params.put("agree", "true");

        ApiRequest result = apiRequest.request("/account", "member-service", HttpMethod.POST, params);
        if (result.getStatus().is2xxSuccessful()) {
            // 토큰 발급
            Map<String, String> loginParams = new HashMap<>();
            loginParams.put("email", email);
            loginParams.put("password", password);

            ApiRequest result2 = apiRequest.request("/account/token", "member-service", HttpMethod.POST, loginParams);
            if (result2.getStatus().is2xxSuccessful() && result2.getData().isSuccess()) {
                return result2.toObj(String.class);
            }
        }

        return null;
    }
}
