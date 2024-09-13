package org.choongang.thesisAdvance.controllers;

import lombok.Data;
import org.choongang.thesis.controllers.ThesisSearch;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class TrendSearch extends ThesisSearch {
    private List<String> job; // 직업 정보

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate sDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate eDate;
}
