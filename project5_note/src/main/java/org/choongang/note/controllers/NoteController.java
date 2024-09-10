package org.choongang.note.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Note", description = "노트 API")
@RestController
@RequiredArgsConstructor
public class NoteController {
}
