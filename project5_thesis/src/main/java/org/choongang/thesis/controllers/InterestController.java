package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.choongang.thesis.entities.Interests;
import org.choongang.thesis.repositories.InterestsRepository;
import org.choongang.thesis.services.InterestSaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Interest", description = "관심사 API")
@RestController
@RequestMapping("/interest")
@RequiredArgsConstructor
public class InterestController {
    private final InterestsRepository interestsRepository;
    private final InterestSaveService interestSaveService;


    @Operation(summary = "회원 관심사 조회", method = "GET")
    @ApiResponse(responseCode = "200")
    @Parameter(name = "email", required = true, description = "경로 변수, 회원 이메일", example = "test01@test.org")
    @GetMapping("/{email}")
    public JSONData interestInfo(@PathVariable("email") String email) {
        List<Interests> interests = interestsRepository.findAllByEmail(email);

        return new JSONData(interests);
    }

    @Operation(summary = "회원 관심사 수정", method = "PATCH")
    @ApiResponse(responseCode = "201")
    @Parameters({
            @Parameter(name = "email", required = true, description = "경로변수, 수정하려는 회원 이메일", example = "test01@test.org"),
            @Parameter(name = "interests", description = "수정하려는 관심사", example = "[\"S-005\", \"N-004\"]")}
    )
    @PatchMapping("/update/{email}")
    public ResponseEntity<Void> update(@PathVariable("email") String email, @RequestBody List<String> _interests) {
        // id
        interestSaveService.save(_interests, email);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
