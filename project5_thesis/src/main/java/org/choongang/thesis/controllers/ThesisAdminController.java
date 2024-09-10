package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="ThesisAdmin", description = "논문 관리자 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ThesisAdminController {
    /**
     * GET - /admin : 논문 목록 - 승인, 미승인, 공개, 미공개 관련 없이 모두 조회 가능
     * 	GET - /admin/info/{tid} : 논문 한개 조회
     *
     * 	PATCH - /admin/update/{tid} : 논문 정보 수정(+ 승인, 공개 범위)
     * 	PATCH - /admin : 논문 목록에서 일괄 승인 처리
     */

    @Operation(summary = "논문 목록", method="GET", description = "미승인, 미열람 논문도 모두 조회 가능")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public JSONData list() {
        return null;
    }
    
    @Operation(summary = "논문 한개 조회", method="GET", description = "미승인, 미열람 논문도 모두 조회 가능")
    @ApiResponse(responseCode = "200")
    @Parameter(name="tid", required = true, description = "경로변수, 논문 등록번호")
    @GetMapping("/info/{tid}")
    public JSONData info(@PathVariable("tid") Long tid) {
        
        return null;
    }
    
    @Operation(summary = "논문 한개 수정", method = "PATCH")
    @ApiResponse(responseCode = "201")
    @Parameters({
            @Parameter(name="tid", required = true, description = "경로변수, 논문 등록번호")
    })
    @PatchMapping("/update/{tid}")
    public ResponseEntity<Void> update(@PathVariable("tid") Long tid) {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary="논문 목록 수정", method = "PATCH")
    @ApiResponse(responseCode = "201")
    @PatchMapping
    public ResponseEntity<Void> updateList() {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
