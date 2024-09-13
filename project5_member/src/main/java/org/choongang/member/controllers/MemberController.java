package org.choongang.member.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.global.exceptions.BadRequestException;
import org.choongang.global.rests.JSONData;
import org.choongang.member.MemberInfo;
import org.choongang.member.MemberUtil;
import org.choongang.member.constants.Job;
import org.choongang.member.entities.Member;
import org.choongang.member.jwt.TokenProvider;
import org.choongang.member.services.MemberDeleteService;
import org.choongang.member.services.MemberInfoService;
import org.choongang.member.services.MemberSaveService;
import org.choongang.member.validators.JoinValidator;
import org.choongang.member.validators.UpdateValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "회원 API")
@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final JoinValidator joinValidator;
    private final UpdateValidator updateValidator;
    private final MemberSaveService saveService;
    private final MemberInfoService infoService;
    private final MemberDeleteService deleteService;
    private final TokenProvider tokenProvider;
    private final Utils utils;
    private final MemberUtil memberUtil;

    /* 로그인 한 회원 정보 조회 */
    @Operation(summary = "인증(로그인)한 회원 정보 조회", method = "GET")
    @ApiResponse(responseCode = "200", description = "로그인 한 회원 정보 조회")
    @GetMapping("/account")
    @PreAuthorize("isAuthenticated()")
    public JSONData info(@AuthenticationPrincipal MemberInfo memberInfo) {
        Member member = memberInfo.getMember();

        return new JSONData(member);
    }

    /* 회원 가입 */
    @Operation(summary = "회원 가입", method = "POST")
    @ApiResponse(responseCode = "201", description = "회원 가입 성공시 201")
    @Parameters({
            @Parameter(name = "email", required = true, description = "이메일"),
            @Parameter(name = "password", required = true, description = "비밀번호"),
            @Parameter(name = "confirmPassword", required = true, description = "비밀번호 확인"),
            @Parameter(name = "userName", required = true, description = "사용자명"),
            @Parameter(name = "mobile", description = "휴대전화번호, 형식 검증 있음"),
            @Parameter(name = "birth", required = true, description = "생년월일"),
            @Parameter(name = "gender", required = true, description = "성별"),
            @Parameter(name = "belonging", description = "소속 분야"),
            @Parameter(name = "interests", description = "관심 분야"),
            @Parameter(name = "agree", required = true, description = "회원 가입 약관 동의")
    })
    @PostMapping("/account")
    // /account 쪽에 Post 방식으로 접근하면 -> 회원 가입
    public ResponseEntity join(@RequestBody @Valid RequestJoin form, Errors errors) {
        // 회원 가입 정보는 JSON 데이터로 전달 -> @RequestBody
        joinValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
            // 검증 실패시 JSON형태로 응답 메시지 반환
        }

        saveService.save(form);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* 로그인 절차 완료 시 토큰(=교환권) 발급 */
    @Operation(summary = "인증 및 토큰 발급", description = "인증 성공시 JWT 토큰 발급")
    @ApiResponse(responseCode = "201", headers = @Header(name = "application/json"), description = "발급 받은 토큰")

    @Parameters({
            @Parameter(name = "email", required = true, description = "이메일"),
            @Parameter(name = "password", required = true, description = "비밀번호")
    })
    @PostMapping("/account/token")
    public JSONData token(@RequestBody @Valid RequestLogin form, Errors errors) {

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        String token = tokenProvider.createToken(form.getEmail(), form.getPassword());

        return new JSONData(token);  // 이상이 없으면 JSONData로 토큰 발급
    }

    /* 회원 정보 수정 */
    @Operation(summary = "회원 정보 수정", method = "PATCH")
    @ApiResponse(responseCode = "201", description = "로그인 한 회원 정보 수정")
    @Parameters({
            @Parameter(name = "email", required = true, description = "변경할 회원의 email(아이디로 사용되므로 변경 불가)", example = "user01@test.org"),
            @Parameter(name = "userName", required = true, description = "회원명", example = "사용자01"),
            @Parameter(name = "password", description = "변경할 비밀번호, 필수는 아니므로 변경 값이 넘어오면 변경 처리함", example = "_aA123456"),
            @Parameter(name = "confirmPassword", description = "password 값이 있다면 확인은 필수항목"),
            @Parameter(name = "mobile", description = "휴대전화번호"),
            @Parameter(name = "job", description = "신분"),
            @Parameter(name = "belonging", description = "소속분야"),
            @Parameter(name = "interests", description = "관심분야"),
    })
    @PutMapping("/account/update")
    @PreAuthorize("isAuthenticated()")
    public JSONData update(@RequestBody @Valid RequestUpdate form, Errors errors) {

        updateValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }

        saveService.save(form);

        Member member = memberUtil.getMember();

        return new JSONData(member);
    }

    /* 회원 탈퇴 */
    @Operation(summary = "회원 탈퇴")
    @ApiResponse(responseCode = "200", description = "회원 탈퇴")
    @PatchMapping("/account/withdraw")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> withdraw() {

        deleteService.withdraw();

        return ResponseEntity.ok().build();
    }

    /* 직업으로 회원목록 조회 */
    /*
    @Operation(summary = "직업으로 회원목록 조회", method = "GET")
    @ApiResponse(responseCode = "200", description = "회원 조회")
    @GetMapping("/job-member")
    public List<Member> getUsersByJob(@RequestParam("job") String job) {
        return infoService.getUsersByJob(job);
    }
    */

    /* 회원 이메일로 직업 조회 */
    @Operation(summary = "회원 이메일로 직업 조회", method = "GET")
    @ApiResponse(responseCode = "200", description = "직업 조회")
    @GetMapping("/member-job")
    public JSONData getJobByEmail(@RequestParam String email) {
        Job job = infoService.getJobByEmail(email);

        return new JSONData(new String[] {job.name(), job.getTitle()});
    }
}