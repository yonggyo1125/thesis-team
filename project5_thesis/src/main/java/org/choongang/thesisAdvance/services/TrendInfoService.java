//package org.choongang.thesisAdvance.services;
//
//import lombok.RequiredArgsConstructor;
//import org.choongang.thesis.entities.UserLog;
//import org.choongang.thesis.repositories.UserLogRepository;
//import org.choongang.thesisAdvance.controllers.TrendSearch;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class TrendInfoService {
//    private final UserLogRepository userLogRepository;
//    private final MemberService memberService;
//
//    public List<String> getPopluarKeywords(String email, LocalDate sDate, LocalDate eDate) {
//        // 1. 직업 조회
//        List<String> job = memberService.getJobByEmails(email);
//
//        // 2. 조회한 직업과 기간을 기준으로 검색 기록 조회
//        TrendSearch trendSearch = new TrendSearch();
//        List<UserLog> userLogs = userLogRepository.findByJobInSearchDateBetween(job, sDate.atStartOfDay(), eDate.atTime(23, 59, 59));
//
//        // 3. 검색어 빈도 계산
//        Map<String, Long> keywordFrequency = userLogs.stream()
//                .collect(Collectors.groupingBy(UserLog::getSearch, Collectors.counting()));
//
//        // 4. 빈도수 기준으로 검색어 정렬
//        return keywordFrequency.entrySet().stream()
//                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }
//
//    /* 이메일로 조회
//    public List<String> getPopluarKeywords(String job, LocalDate sDate, LocalDate eDate) {
//        // 1. RestTemplate을 통해 직업군에 속한 회원 이메일을 외부 서비스에서 조회
//        List<String> emails = memberService.getEmailsByJob(job);
//
//        // 2. 조회한 이메일과 기간을 기준으로 검색 기록 조회
//        TrendSearch trendSearch = new TrendSearch();
//        List<UserLog> userLogs = userLogRepository.findByEmailInAndSearchDateBetween(emails, sDate.atStartOfDay(), eDate.atTime(23, 59, 59));
//
//        // 3. 검색어 빈도 계산
//        Map<String, Long> keywordFrequency = userLogs.stream()
//                .collect(Collectors.groupingBy(UserLog::getSearch, Collectors.counting()));
//
//        // 4. 빈도수 기준으로 검색어 정렬
//        return keywordFrequency.entrySet().stream()
//                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }*/
//}
//
