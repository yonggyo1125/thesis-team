package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.choongang.global.ListData;
import org.choongang.global.Utils;
import org.choongang.global.exceptions.BadRequestException;
import org.choongang.global.rests.JSONData;
import org.choongang.thesis.constants.ApprovalStatus;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.services.ThesisDeleteService;
import org.choongang.thesis.services.ThesisInfoService;
import org.choongang.thesis.services.ThesisSaveService;
import org.choongang.thesis.validators.ThesisValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ThesisAdmin", description = "논문 관리자 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ThesisAdminController {
    private final ThesisValidator thesisValidator;
    private final ThesisInfoService thesisInfoService;
    private final ThesisSaveService thesisSaveService;
    private final ThesisDeleteService thesisDeleteService;
    private final Utils utils;

    /**
     * GET - /admin : 논문 목록 - 승인, 미승인, 공개, 미공개 관련 없이 모두 조회 가능
     * GET - /admin/info/{tid} : 논문 한개 조회
     * PATCH - /admin/update/{tid} : 논문 정보 수정(+ 승인, 공개 범위)
     * PATCH - /admin : 논문 목록에서 일괄 승인 처리
     */
    @Operation(summary = "논문 목록", method = "GET", description = "미승인, 미열람 논문도 모두 조회 가능")
    @ApiResponse(responseCode = "200")
    @Parameters({
            @Parameter(name = "type", description = "경로변수, type=approval 이면 승인된 논문 unpproval이면 미승인 논문, rejected이면 반려된 논문 ")
    })
    @GetMapping(path = {"/list", "/list/{type}"})
    public JSONData list(@PathVariable(name = "type", required = false) String type, @ModelAttribute ThesisSearch search) {
        if (StringUtils.hasText(type)) {
            if (type.equals("approval")) {
                search.setApprovalStatus(ApprovalStatus.APPROVED);  // 승인된 논문만
            } else if (type.equals("unapproval")) {
                search.setApprovalStatus(ApprovalStatus.PENDING);  // 미승인(대기 중) 논문만
            } else if (type.equals("rejected")) {
                search.setApprovalStatus(ApprovalStatus.REJECTED);  // 반려된 논문만
            }
        }
        ListData<Thesis> data = thesisInfoService.getList(search);
        return new JSONData(data);
    }

    @Operation(summary = "논문 한개 조회", method = "GET", description = "미승인, 미열람 논문도 모두 조회 가능")
    @ApiResponse(responseCode = "200")
    @Parameter(name = "tid", required = true, description = "경로변수, 논문 등록번호")
    @GetMapping("/info/{tid}")
    public JSONData info(@PathVariable("tid") Long tid) {
        Thesis item = thesisInfoService.get(tid);

        return new JSONData(item);
    }

    @Operation(summary = "논문 한개 수정", method = "PATCH")
    @ApiResponse(responseCode = "201")
    @Parameters({
            @Parameter(name = "tid", required = true, description = "경로변수, 논문 등록번호")
    })
    @PatchMapping("/update/{tid}")
    public ResponseEntity<Void> update(@PathVariable("tid") Long tid, @Valid @RequestBody RequestThesis form, Errors errors) {
        form.setMode("update");
        form.setTid(tid);
        return save(form, errors);
    }

    @Operation(summary = "논문 삭제", method = "DELETE")
    @ApiResponse(responseCode = "200")
    @Parameter(name = "tid", required = true, description = "경로변수, 논문 등록번호", example = "100")
    @DeleteMapping("/{tid}")
    public void delete(@PathVariable("tid") Long tid) {
        thesisDeleteService.delete(tid);

    }

    @Operation(summary = "논문 목록 수정", method = "PATCH")
    @ApiResponse(responseCode = "201")
    @PatchMapping
    public ResponseEntity<Void> updateList(@RequestBody ThesisApprovalRequest approvalRequest) {
        thesisSaveService.saveTheses(approvalRequest.getTheses());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "논문 목록 삭제", method = "DELETE")
    @ApiResponse(responseCode = "201")
    @DeleteMapping
    public ResponseEntity<Void> deleteList(@RequestBody List<Long> tids) {
        thesisDeleteService.deleteList(tids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    public ResponseEntity<Void> save(RequestThesis form, Errors errors) {
        thesisValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }
        thesisSaveService.save(form);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
