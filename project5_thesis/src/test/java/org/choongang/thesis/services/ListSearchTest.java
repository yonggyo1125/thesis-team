package org.choongang.thesis.services;

import org.choongang.global.ListData;
import org.choongang.thesis.controllers.ThesisSearch;
import org.choongang.thesis.entities.Thesis;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class ListSearchTest {

    @Autowired
    private ThesisInfoService infoService;

    private ThesisSearch search;

    @Test
    @DisplayName("논문 통합 검색 테스트")
    void listSearchTest() {
        search = new ThesisSearch();
        search.setPublisher("학회");
        search.setEDate(LocalDate.now());
//        search.setPoster("임윤진");

        System.out.println(search);

        ListData<Thesis> result = infoService.getList(search);

        System.out.println(result);
    }
}
