package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.global.CommonSearch;
import org.choongang.global.Utils;
import org.choongang.global.exceptions.BadRequestException;
import org.choongang.global.rests.JSONData;
import org.choongang.member.MemberUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Tag(name="Thesis", description = "논문 API")
@RestController
@RequiredArgsConstructor
public class ThesisController {

    private final Utils utils;

    @Operation(summary = "논문 등록", method="POST")
    @ApiResponse(responseCode = "201")
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody RequestThesis form, Errors errors) {
        form.setMode("register");
        return save(form, errors);
    }

    @Operation(summary = "논문 수정", method="PATCH")
    @ApiResponse(responseCode = "201")
    @Parameters({
            @Parameter(name="tid", required = true, description = "경로변수, 논문 등록번호", example="100")
    })
    @PatchMapping("/update/{tid}")
    public ResponseEntity<Void> update(@PathVariable("tid") Long tid, @Valid @RequestBody RequestThesis form, Errors errors) {

        return save(form, errors);
    }

    public ResponseEntity<Void> save(RequestThesis form, Errors errors) {

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

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
    @PreAuthorize("permitAll()")
    public JSONData list() {
        return null;
    }

    @Operation(summary = "내가 등록한 논문 목록", method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/mylist")
    public JSONData mylist(@ModelAttribute CommonSearch search, @AuthenticationPrincipal MemberUtil memberUtil) {

        return null;
    }
}
