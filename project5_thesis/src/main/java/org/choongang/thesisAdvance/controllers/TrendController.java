package org.choongang.thesisAdvance.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.choongang.thesisAdvance.services.TrendInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trend")
public class TrendController {

    private final TrendInfoService trendInfoService;

    @Operation(summary = "직업별 인기 키워드 조회", method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/popular")
    @PreAuthorize("permitAll()")
    public JSONData getKeywordByJob(@ModelAttribute TrendSearch search){
        List<Map<String, Object>> items = trendInfoService.getKeywordRankingByJob(search);

        return new JSONData(items);
    }

    @Operation(summary = "학문 분류별 검색 통계")
    @GetMapping("/popular/field")
    @PreAuthorize("permitAll()")
    public JSONData getFieldRanking(@ModelAttribute TrendSearch search) {
        Map<String, Map<String, Object>> data = trendInfoService.getFieldRanking(search);

        return new JSONData(data);
    }
}