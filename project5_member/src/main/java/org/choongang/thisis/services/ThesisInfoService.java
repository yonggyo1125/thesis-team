package org.choongang.thisis.services;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.ApiRequest;
import org.choongang.thisis.entities.Field;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThesisInfoService {

    private final ApiRequest apiRequest;

    /**
     * 필드 목록 조회
     */

    public List<Field> getFields() {
        ApiRequest result = apiRequest.request("/field/list", "thesis-service");
        if (result.getStatus().is2xxSuccessful() && result.getData().isSuccess()) {
            return result.toList(new TypeReference<>() {});
        }

        return null;
    }
}
