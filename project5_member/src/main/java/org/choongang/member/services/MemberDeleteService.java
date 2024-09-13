package org.choongang.member.services;

import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.member.repositories.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberDeleteService {
    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;

    /* 회원 탈퇴 처리 */
    public void withdraw() {
        Member member = memberUtil.getMember();

        if (memberUtil.isLogin()) {
            member.setDeletedAt(LocalDateTime.now());
        }
        memberRepository.save(member);
    }
}
