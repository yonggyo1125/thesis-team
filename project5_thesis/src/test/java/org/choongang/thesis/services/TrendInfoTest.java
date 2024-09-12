package org.choongang.thesis.services;

import jakarta.servlet.http.HttpServletRequest;
import org.choongang.global.ListData;
import org.choongang.thesis.controllers.ThesisSearch;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.repositories.ThesisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class TrendInfoTest {

    @Autowired
    ThesisInfoService infoService;

    @Autowired
    ThesisRepository thesisRepository;

    private HttpServletRequest request;

    /*
    @BeforeEach
    void Data() {
        for(int i = 0; i < 10; i++){
            Thesis thesis = new Thesis();
            thesis.setCategory(Category.DOMESTIC);
            thesis.setPoster("편집자"+i);
            thesis.setTitle("제목"+i);
            thesis.setGid("그룹 ID"+i);
            thesis.setUserName("userName"+i);
            thesis.setEmail("test"+i+"@email.org");

            thesisRepository.saveAndFlush(thesis);
        }
    }*/

    @Test
    @DisplayName("기간별 논문 조회 테스트")
    void test1() {
        LocalDate eDate = LocalDate.now().plusDays(1L);
        ThesisSearch search = new ThesisSearch();
        search.setEDate(eDate);
        ListData<Thesis> theses = infoService.getList(search);
        //theses.getItems().forEach(System.out::println);
        System.out.println("Theses count: " + theses.getItems().size());
        //theses.getItems().forEach(i ->System.out.println("Thesis: " + i));
    }
}
