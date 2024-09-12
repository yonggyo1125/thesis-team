package org.choongang.thesis.controllers;

import lombok.Data;
import org.choongang.global.CommonSearch;
import org.choongang.thesis.constants.ApprovalStatus;

import java.time.LocalDate;
import java.util.List;

@Data
public class ThesisSearch extends CommonSearch {
    private List<String> category;
    private List<String> fields;
    private ApprovalStatus approvalStatus; //승인 상태 필드
    private List<String> email; //회원 이메일(로그인 ID)

    private LocalDate sDate; //발행일 검색 시작일
    private LocalDate eDate; //발행일 검색 종료일

    private String title; //제목
    private String poster; //저자
    private String thAbstract; // 초록
    private String reference; // 참고문헌
    private String publisher; // 발행기관
    private String language; // 언어
    private String country; // 국가

    private String sort; //정렬 조건
}
