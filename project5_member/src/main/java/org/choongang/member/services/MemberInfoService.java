package org.choongang.member.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.services.FileInfoService;
import org.choongang.global.ListData;
import org.choongang.global.Pagination;
import org.choongang.member.MemberInfo;
import org.choongang.member.constants.Authority;
import org.choongang.member.constants.Job;
import org.choongang.member.controllers.MemberSearch;
import org.choongang.member.entities.Member;
import org.choongang.member.entities.QMember;
import org.choongang.member.repositories.MemberRepository;
import org.choongang.thisis.entities.Interests;
import org.choongang.thisis.services.InterestSaveService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final JPAQueryFactory queryFactory;
    private final HttpServletRequest request;
    private final FileInfoService fileInfoService;
    private final InterestSaveService interestSaveService;

    /* 회원 정보가 필요할 때마다 호출 되는 메서드 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Authority authority = Objects.requireNonNullElse(member.getAuthorities(), Authority.USER);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority.name()));

        List<FileInfo> files = fileInfoService.getList(member.getGid());
        // if (files != null && !files.isEmpty()) member.setProfileImage(files.get(0));

        List<Interests> interests = interestSaveService.interestInfo(member.getEmail());
        member.setInterests(interests);

        return MemberInfo.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .member(member)
                .authorities(authorities)
                .build();
    }


    /**
     * 회원 목록 조회
     *
     * @param search
     * @return
     */
    @Transactional
    public ListData<Member> getList(MemberSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;
        int offset = (page - 1) * limit;

        /* 검색 처리 S */
        BooleanBuilder andBuilder = new BooleanBuilder();
        QMember member = QMember.member;

        String sopt = search.getSopt();
        String skey = search.getSkey();
        sopt = StringUtils.hasText(sopt) ? sopt.toUpperCase() : "ALL";

        if (StringUtils.hasText(skey)) {
            /**
             * sopt 검색옵션
             * ALL - (통합검색) - email, userName
             * email - 이메일로 검색
             * userName - 회원명으로 검색
             * job - 직업으로 검색
             */
            sopt = sopt.trim();
            skey = skey.trim();
            BooleanExpression condition = null;

            if (sopt.equals("ALL")) { // 통합 검색
                condition = member.email.contains(skey).or(member.userName.contains(skey)).or(member.job.stringValue().contains(skey));

            } else if (sopt.equals("email")) { // 이메일로 검색
                condition = member.email.contains(skey);

            } else if (sopt.equals("userName")) { // 회원명
                condition = member.userName.contains(skey);

            } else if (sopt.equals("job")) {
                // 직업으로 검색
                condition = member.job.stringValue().contains(skey);
            }

            if (condition != null) andBuilder.and(condition);
        }

        List<String> job = search.getJob();
        if (job != null && !job.isEmpty()) {
            ///andBuilder.and(member.job.in(job));
        }

        /* 검색 처리 E */

        List<Member> items = queryFactory.selectFrom(member)
                .fetchJoin()
                .where(andBuilder)
                .offset(offset)
                .limit(limit)
                .orderBy(member.createdAt.desc())
                .fetch();

        long total = memberRepository.count(andBuilder);
        Pagination pagination = new Pagination(page, (int) total, 10, limit, request);

        return new ListData<>(items, pagination);
    }

    public void addInfo(Member member) {
        List<FileInfo> files = fileInfoService.getList(member.getGid());
        if (files != null && !files.isEmpty()) {
            // member.setProfileImage(files.get(0));
        }
    }

    /* 직업으로 회원 목록 검색 */
    public List<Member> getUsersByJob(Job job) {
        return memberRepository.findByJob(job);
    }

    /* 회원 이메일로 직업 검색 */
    public Job getJobByEmail(String email) {
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        return memberOptional.map(Member::getJob)
                .orElse(Job.GENERAL_MEMBER);
    }
}