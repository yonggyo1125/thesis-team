package org.choongang.thesis.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InterestRepositoryTest {
    @Autowired
    private InterestsRepository interestsRepository;

    @Test
    void test1(){
        System.out.println(interestsRepository.findIdsByEmail("test02@test.org"));
    }
}
