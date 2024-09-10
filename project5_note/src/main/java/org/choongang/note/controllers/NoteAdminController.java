package org.choongang.note.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/register")
    public ResponseEntity<Void> register() {

        return save();
    }

    @PatchMapping("/update/{nid}")
    public ResponseEntity<Void> update(@PathVariable("nid") String nid) {
        return save();
    }

    public ResponseEntity<Void> save() {

        String method = request.getMethod().toUpperCase();
        HttpStatus status = method.equals("PATCH") ? HttpStatus.OK : HttpStatus.CREATED;

        return ResponseEntity.status(status).build();
    }
}
