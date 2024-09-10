package org.choongang.thesis.controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.choongang.file.entities.FileInfo;

import java.util.List;
import java.util.UUID;

@Data
public class RequestThesis {

    private Long tid;

    @NotBlank
    private String mode; // register, update

    @NotBlank
    private String category;

    @NotNull
    private List<String> fields; // 학문별 분류 코드 목록

    @NotBlank
    private String poster; // 편집자

    @NotBlank
    private String title; // 제목

    private String contributor; // 기여자

    private String thAbstract; // 초록

    private String reference; // 참고 문헌

    @NotBlank
    private String gid = UUID.randomUUID().toString(); // 그룹 ID

    private boolean visible; //  공개 여부
    private boolean approval; // 승인 여부

    private String publisher; // 발행기관

    private String toc; // 목차

    private String language; // 언어

    private String country; // 국가

    private FileInfo fileInfo; // 논문 파일
}
