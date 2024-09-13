package org.choongang.member.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.choongang.global.Utils;
import org.choongang.global.exceptions.BadRequestException;
import org.choongang.global.rests.ApiRequest;
import org.choongang.global.tests.TestTokenService;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Gender;
import org.choongang.member.constants.Job;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.member.services.MemberInfoService;
import org.choongang.member.services.MemberSaveService;
import org.choongang.thisis.entities.Field;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
//@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    private TestTokenService tokenService;

    @Autowired
    private MemberSaveService saveService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberInfoService memberInfoService;

    private RequestJoin form;
    @Autowired
    private ApiRequest apiRequest;
    @Autowired
    private Utils utils;

    void init() {
        for (long i = 1L; i <= 10L; i++) {
            form = new RequestJoin();
            form.setEmail("user" + i + "@test.org");
            form.setPassword("_aA12345678");
            form.setConfirmPassword(form.getPassword());
            form.setUserName("사용자" + i);
            form.setMobile("010-1000-1000");
            form.setJob("PROFESSOR");
            form.setBirth(LocalDate.of(1990, 1, 1));
            form.setGender("MALE");
            form.setAgree(true);
            System.out.println(form);
            saveService.save(form);
        }
    }

    @Test
    void test1() {
        ApiRequest result = apiRequest.request("/field/list", "thesis-service", HttpMethod.GET);
        if (!result.getStatus().is2xxSuccessful()) {
            throw new BadRequestException(utils.getMessage("Fail.Field.Get"));
        }
        List<Field> fieldsList = result.toList(new TypeReference<List<Field>>() {});
//        System.out.println(fieldsList.get(0).getId());
        for (long i = 1L; i <= 10L; i++) {
            RequestJoin form = RequestJoin.builder()
                    .email("mock" + i + "@test.org")
                    .userName("mock사용자"+i)
                    .password("_aA123456")
                    .confirmPassword("_aA123456")
                    .birth(LocalDate.of(1990, 1, 1))
                    .gender(Gender.MALE.name())
                    .mobile("01012341234")
                    .job(Job.PROFESSOR.toString())
                    .memMajor(fieldsList.get(0).getId())
                    .memMinor(fieldsList.get((int) i + 10).getId())
                    .interests(List.of(fieldsList.get((int) i + 20).getId(), fieldsList.get((int) i + 30).getId(), fieldsList.get((int) i + 40).getId()))
                    .agree(true)
                    .build();
            System.out.println(form);
            saveService.save(form);
        }
    }

    @Test
    @DisplayName("회원 가입 테스트")
    void joinTest() throws Exception {
        String params = om.writeValueAsString(form);

        String token = tokenService.getToken(Authority.USER);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authentication", "Bearer " + token)
                        .characterEncoding(Charset.forName("UTF-8"))
                        .content(params))
                .andDo(print());
    }

    @Test
    @DisplayName("직업으로 회원 목록 조회 테스트")
    public void testGetUsersByJob() {
        Job job = Job.PROFESSOR;
        List<Member> member = memberInfoService.getUsersByJob(job);
        assertEquals(10, member.size());
        System.out.println(member);
    }

    @Test
    @DisplayName("회원 이메일로 직업 조회 테스트")
    void testGetJobByEmailFound() {
        String email = "user1@test.org";
        Job job = memberInfoService.getJobByEmail(email);
        assertEquals(Job.PROFESSOR, job);
        System.out.println(job);
    }

    @Test
    void test2() {
        apiRequest.setTest(true);
        ApiRequest result = apiRequest.request("/account", "member-service");
    }
}
