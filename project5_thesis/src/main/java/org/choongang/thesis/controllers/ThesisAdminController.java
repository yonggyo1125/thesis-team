package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="ThesisAdmin", description = "논문 관리자 API")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class ThesisAdminController {
    /**
     * GET - /admin : 논문 목록 - 승인, 미승인, 공개, 미공개 관련 없이 모두 조회 가능
     * 	GET - /admin/info/{tid} : 논문 한개 조회
     *
     * 	PATCH - /admin/save : 논문 정보 수정(+ 승인, 공개 범위)
     * 	PATCH - /admin : 논문 목록에서 일괄 승인 처리
     */

    @GetMapping
    public JSONData list() {
        return null;
    }
}
