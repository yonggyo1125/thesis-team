package org.choongang.thesis.services;

import org.choongang.global.ListData;
import org.choongang.member.MemberUtil;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.controllers.ThesisSearch;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.repositories.ThesisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThesisInfoServiceTest {

    @Autowired
    ThesisInfoService infoService;
    @Autowired// 서비스 클래스
    ThesisRepository thesisRepository;

    @Mock
    MemberUtil memberUtil;  // MemberUtil 모킹

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 10; i++) {
            Thesis thesis = new Thesis();
            thesis.setCategory(Category.DOMESTIC);
            thesis.setPoster("편집자"+i);
            thesis.setTitle("제목"+i);
            thesis.setGid("그룹 ID"+i);
            thesis.setUserName("userName"+i);
            thesis.setEmail("test"+i+"@email.org");
            thesisRepository.saveAndFlush(thesis);
        }
    }

    @Test
    @DisplayName("전체 논문 목록 조회 테스트")
    void testGetAllThesis() {
        ThesisSearch search = new ThesisSearch();
        search.setPage(1);
        search.setLimit(10);


        ListData<Thesis> result = infoService.getList(search);

        // 결과 검증 (논문 목록이 0보다 큰지 확인)
        System.out.println(result);
    }
}
