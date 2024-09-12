package org.choongang.thesis.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.choongang.thesis.entities.Interests;
import org.choongang.thesis.exceptions.InterestNotFoundException;
import org.choongang.thesis.repositories.FieldRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class InterestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FieldRepository fieldRepository;
    @Qualifier("objectMapper")
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("관심사 조회 테스트")
    void test1() throws Exception {
        mockMvc.perform(get("/interest/test02@test.org")).andDo(print());
    }

    @Test
    @DisplayName("관심사 저장 테스트")
    void test2() throws Exception {
        String email = "test04@test.org";
        List<Interests> list = List.of(
                new Interests(fieldRepository.findById("SS-Law").orElseThrow(InterestNotFoundException::new).getId(), email),
                new Interests(fieldRepository.findById("AE-Ecology").orElseThrow(InterestNotFoundException::new).getId(), email),
                new Interests(fieldRepository.findById("BE-Marketing").orElseThrow(InterestNotFoundException::new).getId(), email)
        );

        List<String> ids = list.stream().map(Interests::getId).toList();
        //Map<String, Object> data = new HashMap<>();
        // data.put("email", email);
        //data.put("_interests", ids);

        String body = objectMapper.writeValueAsString(ids);
        System.out.println("body:" + body);
        mockMvc.perform(patch("/interest/update/" + email).contentType(MediaType.APPLICATION_JSON).content(body)).andDo(print());
    }
}
