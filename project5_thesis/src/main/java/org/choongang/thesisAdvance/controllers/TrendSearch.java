package org.choongang.thesisAdvance.controllers;

import lombok.Data;
import org.choongang.thesis.controllers.ThesisSearch;

import java.time.LocalDate;

// 여기는 필요없는 엔티티 같습니다...
@Data
public class TrendSearch extends ThesisSearch {
    private String job; // 직업 정보
    private LocalDate sDate;
    private LocalDate eDate;
}
