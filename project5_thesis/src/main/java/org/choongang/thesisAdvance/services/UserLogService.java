package org.choongang.thesisAdvance.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.choongang.member.MemberUtil;
import org.choongang.member.entities.Member;
import org.choongang.thesis.entities.QUserLog;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.entities.UserLog;
import org.choongang.thesis.exceptions.ThesisNotFoundException;
import org.choongang.thesis.repositories.ThesisRepository;
import org.choongang.thesis.repositories.UserLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserLogService {
    private final MemberUtil memberUtil;
    private final UserLogRepository userLogRepository;
    private final ThesisRepository thesisRepository;
    private final JPAQueryFactory queryFactory;

    /**
     * @Id @GeneratedValue
     * private Long logSeq;
     * @ManyToOne(fetch = FetchType.LAZY)
     * @JoinColumn(name="tid") private Thesis thesis;
     * @CreatedBy
     * @Column(length=80, nullable = false)
     * private String email;
     * @Column(length=80, nullable = false)
     * private String search; // 검색어
     * @CreatedDate private LocalDateTime searchDate; // 검색일
     */
    /**
     * 검색 검색어 기록
     *
     * @param keyword
     */
    public void save(String keyword) {
/*        if (!memberUtil.isLogin() || !StringUtils.hasText(keyword.trim())) {
            return;
        }*/
        try {
            Member member = memberUtil.getMember();
            UserLog userLog = UserLog.builder()
//                    .email(memberUtil.getMember().getEmail())
                    //.email(SecurityContextHolder.getContext().getAuthentication().getName())
                    .job(member.getJob())
                    .search(keyword)
                    .build();

            userLogRepository.saveAndFlush(userLog);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("검색 기록 실패");
        }
    }

    /**
     * 논문 조회 기록
     * @param tid
     */
    public void save(Long tid) {
//        if (!memberUtil.isLogin() || tid == null){
//            System.out.println("user 정보 또는 게시글 번호 없음");
//            return;
//        }
        try{
            UserLog userLog = UserLog.builder()
//                    .email(memberUtil.getMember().getEmail())
                    .email(SecurityContextHolder.getContext().getAuthentication().getName())
                    .thesis(thesisRepository.findById(tid).orElseThrow(ThesisNotFoundException::new))
                    .build();
            userLogRepository.saveAndFlush(userLog);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("논문 조회 기록 실패");
        }
    }
    public List<Thesis> getRecentlyViewedTheses(String email) {
        List<UserLog> logs = userLogRepository.findByEmailOrderBySearchDateDesc(email);
        List<Long> tids = logs.stream().map(log -> log.getThesis().getTid()).collect(Collectors.toList());
        return thesisRepository.findAllById(tids);

    }


}
