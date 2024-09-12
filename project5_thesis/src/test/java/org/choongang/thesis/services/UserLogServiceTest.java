package org.choongang.thesis.services;

import org.choongang.global.ListData;
import org.choongang.thesis.controllers.ThesisSearch;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesisAdvance.services.UserLogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLogServiceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserLogService userLogService;

    @Test
    @DisplayName("검색 키워드 저장 서비스 테스트")
    @WithMockUser(username = "test01@test.org")
    @WithUserDetails()
    void test1() throws Exception {
        ThesisSearch search = new ThesisSearch();
        search.setSkey("테스트 키워드123");
        ListData<Thesis> mockData = new ListData<>();

        mockMvc.perform(get("/list?skey=테스트"))
                .andDo(print());
    }

    @Test
    @WithMockUser(username = "user01@test.org")
    void test2() {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
    }

    @Test
    @DisplayName("논문 조회 저장 서비스 테스트")
    @WithMockUser(username = "test01@test.org")
    void test3() throws Exception {
        //테스트 시 1. 환경변수 2. 멤버 인증 확인 3. addinfo 주석
        for(int i=0;i<5;i++){
            mockMvc.perform(get("/info/610"));
        }
    }

    @Test
    @DisplayName("최근 본 논문 조회 테스트")
    @WithMockUser(username = "test01@test.org")
    void test4() throws Exception{
        for(int i=0;i<5;i++){
        mockMvc.perform(get("/myView"));
        }
    }
}
