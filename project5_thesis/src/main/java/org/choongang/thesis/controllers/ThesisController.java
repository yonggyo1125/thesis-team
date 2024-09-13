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
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.services.ThesisDeleteService;
import org.choongang.thesis.services.ThesisInfoService;
import org.choongang.thesis.services.ThesisSaveService;
import org.choongang.thesis.services.ThesisViewService;
import org.choongang.thesis.validators.ThesisValidator;
import org.choongang.thesisAdvance.services.UserLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Thesis", description = "논문 API")
@RestController
@RequiredArgsConstructor
public class ThesisController {
    private final ThesisValidator thesisValidator;
    private final ThesisSaveService thesisSaveService;
    private final ThesisDeleteService thesisDeleteService;
    private final ThesisInfoService thesisInfoService;
    private final ThesisViewService thesisViewService;
    private final Utils utils;
    private final UserLogService userLogService;


    @Operation(summary = "논문 등록", method = "POST")
    @ApiResponse(responseCode = "201")
    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody RequestThesis form, Errors errors) {
        form.setMode("register");
        form.setActionType("submit");
        return save(form, errors);
    }

    @Operation(summary = "논문 수정", method = "PATCH")
    @ApiResponse(responseCode = "201")
    @Parameters({
            @Parameter(name = "tid", required = true, description = "경로변수, 논문 등록번호", example = "100"),
            @Parameter(name = "action", required = true, description = "수정 또는 재제출",example = "submit or resubmit")
    })
    @PatchMapping("/update/{tid}/{action}")
    public ResponseEntity<Void> update(@PathVariable("tid") Long tid,@PathVariable("action") String action,@Valid @RequestBody RequestThesis form, Errors errors) {
        form.setActionType(action);
        form.setMode("update");
        form.setTid(tid);
        return save(form, errors);
    }


    public ResponseEntity<Void> save(RequestThesis form, Errors errors) {
        thesisValidator.validate(form, errors);

        if (errors.hasErrors()) {
            throw new BadRequestException(utils.getErrorMessages(errors));
        }
        if ("resubmit".equals(form.getActionType())) {
            //재등록입니다
            thesisSaveService.resubmitThesis(form.getTid(), form);
        } else {
            thesisSaveService.save(form);

        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "논문 삭제", method = "DELETE")
    @ApiResponse(responseCode = "200")
    @Parameter(name = "tid", required = true, description = "경로변수, 논문 등록번호", example = "100")
    @DeleteMapping("/{tid}")
    public void delete(@PathVariable("tid") Long tid) {
        thesisDeleteService.delete(tid);

    }

    @Operation(summary = "논문 한개 조회", method = "GET")
    @ApiResponse(responseCode = "200")
    @Parameter(name = "tid", required = true, description = "경로변수, 논문 등록번호", example = "100")
    @GetMapping("/info/{tid}")
    @PreAuthorize("permitAll()")
    public JSONData info(@PathVariable("tid") Long tid) {
        thesisViewService.updateViewCount(tid); //조회수 추가
        thesisViewService.dailylog(tid);
        Thesis item = thesisInfoService.get(tid);

        userLogService.save(tid);//조회한 논문 번호 저장

        return new JSONData(item);

    }

    @Operation(summary = "논문 목록 조회", method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/list")
    @PreAuthorize("permitAll()")
    public JSONData list(@ModelAttribute ThesisSearch search) {
        ListData<Thesis> data = thesisInfoService.getList(search);

        return new JSONData(data);
    }

    @Operation(summary = "내가 등록한 논문 목록", method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/mylist")
    public JSONData mylist(@ModelAttribute ThesisSearch search) {
        ListData<Thesis> data = thesisInfoService.getMyList(search);

        return new JSONData(data);
    }

    @Operation(summary = "최근 본 논문 목록", method = "GET")
    @GetMapping("/myView")
    public JSONData myView() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Thesis> recentTheses = userLogService.getRecentlyViewedTheses(email);
        //System.out.println(recentTheses);
        return new JSONData(recentTheses);
    }
}
