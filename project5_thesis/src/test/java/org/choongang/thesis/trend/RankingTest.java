package org.choongang.thesis.trend;

import org.choongang.global.tests.MockMember;
import org.choongang.member.MemberUtil;
import org.choongang.thesis.entities.WishList;
import org.choongang.thesis.repositories.WishListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class RankingTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberUtil memberUtil;

    @Autowired
    private WishListRepository wishListRepository;

    @Test
    @MockMember
    void test1() {
        //Member member = memberUtil.getMember();
        //System.out.println(member);
        WishList wishList = new WishList();
        wishList.setTid(1L);
        wishListRepository.saveAndFlush(wishList);
    }

    @Test
    @MockMember
    void test2() throws Exception {
        mockMvc.perform(get("/trend/popular/field"))
                .andDo(print());
    }
}
