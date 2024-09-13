package org.choongang.thesis.trend;

import org.choongang.global.tests.MockMember;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RankingTest {

    @Autowired
    private MemberUtil memberUtil;

    @Test
    @MockMember
    void test1() {
        Member member = memberUtil.getMember();
        System.out.println(member);
    }
}
