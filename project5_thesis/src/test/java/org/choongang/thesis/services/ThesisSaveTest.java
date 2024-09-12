package org.choongang.thesis.services;

import org.choongang.thesis.constants.Category;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.repositories.ThesisRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThesisSaveTest {
    @Autowired
    ThesisRepository thesisRepository;

    @Test
    @DisplayName("가짜 논문 데이터")
    void test1() {
        for (int i = 0; i < 10; i++) {
            Thesis thesis = new Thesis();
            thesis.setCategory(Category.DOMESTIC);
            thesis.setPoster("편집자"+i);
            thesis.setTitle("제목"+i);
            thesis.setGid(Integer.toString(i));
            thesis.setUserName("userName"+i);
            thesis.setEmail("test"+i+"@email.org");
            thesisRepository.saveAndFlush(thesis);
        }
    }
}
