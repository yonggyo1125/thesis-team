package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "논문 등록")
    @ApiResponse(responseCode = "201")
    @PostMapping
    public ResponseEntity<Void> register() {

        return save();
    }

    @Operation(summary = "논문 수정")
    @ApiResponse(responseCode = "201")
    @PatchMapping("/update/{tid}")
    public ResponseEntity<Void> update(@PathVariable("tid") Long tid) {

        return save();
    }

    public ResponseEntity<Void> save() {

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
