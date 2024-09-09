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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name="Thesis", description = "논문 API")
@RestController
@RequiredArgsConstructor
public class ThesisController {
    /**
     * POST - /  : 논문 등록
     * 	PATCH - /update/{tid} : 논문 수정
     * 	DELETE - /{tid} : 논문 삭제
     *
     * 	GET - /info/{tid} : 논문 한개 조회
     * 	GET - /list : 전체 논문 조회(모든 논문) : 승인되고, 공개 중인 논문만 조회
     * 	GET - /mylist : 회원 자신이 등록한 논문 목록
     */

    @Operation(summary = "논문 등록", method="POST")
    @ApiResponse(responseCode = "201")
    @PostMapping
    public ResponseEntity<Void> register() {

        return save();
    }

    @Operation(summary = "논문 수정", method="PATCH")
    @ApiResponse(responseCode = "201")
    @Parameters({
            @Parameter(name="tid", required = true, description = "경로변수, 논문 등록번호", example="100")
    })
    @PatchMapping("/update/{tid}")
    public ResponseEntity<Void> update(@PathVariable("tid") Long tid) {

        return save();
    }

    public ResponseEntity<Void> save() {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "논문 삭제", method="DELETE")
    @ApiResponse(responseCode = "200")
    @Parameter(name="tid", required = true, description = "경로변수, 논문 등록번호", example = "100")
    @DeleteMapping("/{tid}")
    public void delete(@PathVariable("tid") Long tid) {

    }

    @Operation(summary = "논문 한개 조회", method="GET")
    @ApiResponse(responseCode = "200")
    @Parameter(name="tid", required = true, description = "경로변수, 논문 등록번호", example = "100")
    @GetMapping("/info/{tid}")
    @PreAuthorize("permitAll()")
    public JSONData info(@PathVariable("tid") Long tid) {

        return null;
    }

    @Operation(summary = "논문 목록 조회", method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/list")
    public JSONData list() {
        return null;
    }
}
