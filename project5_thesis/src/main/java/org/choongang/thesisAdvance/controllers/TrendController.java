//package org.choongang.thesisAdvance.controllers;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import lombok.RequiredArgsConstructor;
////import org.choongang.thesisAdvance.services.TrendInfoService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/trend")
//public class TrendController {
//
//    private final TrendInfoService trendInfoService;
//
//    @Operation(summary = "직업별 인기 키워드 조회", method = "GET")
//    @ApiResponse(responseCode = "200")
//    @GetMapping("/popular")
//    @PreAuthorize("permitAll()")
//    public ResponseEntity<List<String>> getKeywordByJob(
//            @RequestParam String job,
//            @RequestParam LocalDate sDate,
//            @RequestParam LocalDate eDate
//            ){
//        List<String> popularKeywords = trendInfoService.getPopluarKeywords(job, sDate, eDate);
//
//        return ResponseEntity.ok(popularKeywords);
//    }
//}
