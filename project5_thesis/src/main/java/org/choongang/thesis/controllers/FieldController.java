package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.choongang.thesis.repositories.FieldRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name="field",description = "학문 분류 코드 관련 API")
@RequestMapping("/field")
@RestController
@RequiredArgsConstructor
public class FieldController {


    private final FieldRepository fieldRepository;

    @Operation(summary = "필드 목록 불러오기",method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/list")
    @PreAuthorize("permitAll()")
    public JSONData getFieldList() {

        return new JSONData(fieldRepository.findAll());
    }


}
