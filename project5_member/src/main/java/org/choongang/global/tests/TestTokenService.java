package org.choongang.global.tests;

import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestTokenService {
    private final ApiRequest apiRequest;

    public String getToken() {

        return null;
    }
}
