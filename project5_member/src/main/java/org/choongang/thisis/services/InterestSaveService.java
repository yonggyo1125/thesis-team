package org.choongang.thisis.services;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.choongang.thisis.entities.Interests;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterestSaveService {

    private final ApiRequest apiRequest;

    /**
     * 회원 관심사 조회
     */
    public List<Interests> interestInfo (String email) {
        ApiRequest result = apiRequest.request("/interest/" + email, "thesis-service");

        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toList(new TypeReference<>() {});
        }

        return null;
    }


    /**
     * 회원 관심사 수정
     */

    private boolean update(List<String> _interests, String email) {
        ApiRequest result = apiRequest.request("/interest/update/" + email, "thesis-service", HttpMethod.PATCH, _interests);

        return result.getStatus().is2xxSuccessful();
    }
}
