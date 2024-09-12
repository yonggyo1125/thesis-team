package org.choongang.thesis.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@EnableWebMvc
@AutoConfigureMockMvc
public class FieldControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("필드 데이터 불러오기")
    void test() throws Exception {
        mockMvc.perform(get("/field/list")).andDo(print());
    }
}
