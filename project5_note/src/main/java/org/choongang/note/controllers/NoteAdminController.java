package org.choongang.note.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.global.rests.JSONData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="NoteAdmin", description = "노트 관리자 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class NoteAdminController {

    private final HttpServletRequest request;

    /**
     * Note 설정 등록, 수정  - POST /admin/register, PATCH /admin/update/{nid}  - /admin/save
     * Note 설정 목록 조회 GET /admin/list
     * Note 설정 한개 조회 GET /admin/info/{nid}
     * Note 설정 삭제  DELETE /admin/{nid}
     */

    @Operation(summary = "노트 설정 등록", method="POST")
    @ApiResponse(responseCode = "201")
    @PostMapping("/register")
    public ResponseEntity<Void> register() {

        return save();
    }

    @Operation(summary = "노트 설정 수정", method="PATCH")
    @ApiResponse(responseCode = "200")
    @PatchMapping("/update/{nid}")
    public ResponseEntity<Void> update(@PathVariable("nid") String nid) {
        return save();
    }

    public ResponseEntity<Void> save() {

        String method = request.getMethod().toUpperCase();
        HttpStatus status = method.equals("PATCH") ? HttpStatus.OK : HttpStatus.CREATED;

        return ResponseEntity.status(status).build();
    }

    @Operation(summary = "노트 설정 목록", method="GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/list")
    public JSONData list() {

        return null;
    }

    @Operation(summary="노트 설정 한개 조회", method="GET")
    @ApiResponse(responseCode = "200")
    @GetMapping("/info/{nid}")
    public JSONData info(@PathVariable("nid") String nid) {
        return null;
    }

    @Operation(summary = "노트 설정 삭제", method="DELETE")
    @ApiResponse(responseCode = "200")
    @DeleteMapping("/{nid}")
    public void delete(@PathVariable("nid") String nid) {

    }
}
