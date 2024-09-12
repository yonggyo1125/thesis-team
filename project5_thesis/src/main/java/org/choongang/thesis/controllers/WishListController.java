package org.choongang.thesis.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.choongang.global.ListData;
import org.choongang.global.rests.JSONData;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.services.ThesisInfoService;
import org.choongang.thesis.services.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Wish", description = "위시리스트 API")
@RestController
@RequestMapping("/wish")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    private final ThesisInfoService thesisInfoService;

    @Operation(summary = "위시리스트 목록 조회", method = "GET")
    @ApiResponse(responseCode = "200")
    @GetMapping
    public JSONData list(@ModelAttribute ThesisSearch search) {

        ListData<Thesis> data = thesisInfoService.getWishList(search);

        return new JSONData(data);
    }

    //위시 리스트 추가
    @Operation(summary = "위시리스트 추가", method = "GET")
    @ApiResponse(responseCode = "200")
    @Parameter(name = "tid", required = true, description = "경로변수, 찜한 논문 번호")
    @GetMapping("/{tid}")
    public ResponseEntity<Void> add(@PathVariable("tid") Long tid) {

        wishListService.add(tid);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //삭제
    @Operation(summary = "위시리스트 삭제", method = "DELETE")
    @ApiResponse(responseCode = "200")
    @Parameter(name = "tid", required = true, description = "경로변수, 찜한 논문 번호")
    @DeleteMapping("/{tid}")
    public ResponseEntity<Void> remove(@PathVariable("tid") Long tid){

        wishListService.remove(tid);

        return ResponseEntity.ok().build();
    }

}
