package org.choongang.thesisAdvance.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.choongang.global.ListData;
import org.choongang.global.rests.JSONData;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.repositories.FieldRepository;
import org.choongang.thesis.repositories.InterestsRepository;
import org.choongang.thesisAdvance.services.RecommendInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recommend")
public class RecommendController {
    private final RecommendInfoService recommendInfoService;
    private final MemberUtil memberUtil;
    private final FieldRepository fieldRepository;
    private final InterestsRepository interestsRepository;

    @Operation(summary = "추천 논문 조회", method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/list")
    @PreAuthorize("permitAll()")
    public JSONData list(@ModelAttribute RecommendSearch recommendSearch) {
        Member member = memberUtil.getMember();
//        if(member==null){
//            throw new BadRequestException("Login.Required");
//        }
        ListData<Thesis> data = recommendInfoService.getList("test01@test.org", recommendSearch);
        return new JSONData(data);
    }

}
