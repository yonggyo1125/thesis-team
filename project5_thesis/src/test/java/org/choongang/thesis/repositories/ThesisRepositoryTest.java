package org.choongang.thesis.repositories;

import org.choongang.thesis.constants.Category;
import org.choongang.thesis.entities.Thesis;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThesisRepositoryTest {
    @Autowired
    ThesisRepository thesisRepository;

    @Test
    @DisplayName("가짜 논문 데이터, 조회수 추가")
    void test1() {
        for (int i = 0; i < 10; i++) {
            Thesis thesis = new Thesis();
            thesis.setCategory(Category.DOMESTIC);
            thesis.setPoster("편집자"+i);
            thesis.setTitle("제목"+i);
            thesis.setGid("그룹 ID"+i);
            thesis.setUserName("userName"+i);
            thesis.setEmail("test"+i+"@email.org");
            thesis.setViewCount(i);
            thesisRepository.saveAndFlush(thesis);
        }
    }
}

