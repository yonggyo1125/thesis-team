package org.choongang.thesis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.thesis.entities.*;
import org.choongang.thesis.repositories.ThesisRepository;
import org.choongang.thesis.repositories.ThesisViewDailyRepository;
import org.choongang.thesis.repositories.ThesisViewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThesisViewService {

    private final ThesisViewDailyRepository dailyRepository;
    private final ThesisViewRepository viewRepository;
    private final ThesisRepository thesisRepository;
    private final ThesisInfoService infoService;
    private final MemberUtil memberUtil;
    private final Utils utils;

    public void dailylog(Long tid) {
        try {
            Thesis item = infoService.get(tid);
            List<Field> fields = item.getFields();
            String _fields = fields == null || fields.isEmpty() ? null : fields.stream().map(Field::getId).collect(Collectors.joining(","));

            int uid = memberUtil.isLogin() ? Objects.hash(memberUtil.getMember().getEmail()) : utils.guestUid();

            ThesisViewDaily thesisView = ThesisViewDaily.builder()
                    .uid(uid)
                    .tid(tid)
                    .date(LocalDate.now())
                    .fields(_fields)
                    .build();
            dailyRepository.saveAndFlush(thesisView);

        } catch (Exception e) {}
    }

    public void updateViewCount(Long tid) {
        try {
            Thesis item = infoService.get(tid);

            int uid = memberUtil.isLogin() ? Objects.hash(memberUtil.getMember().getEmail()) : utils.guestUid();

            ThesisView view = new ThesisView();
            view.setTid(tid);
            view.setUid(uid);

            viewRepository.saveAndFlush(view);

            QThesisView thesisView = QThesisView.thesisView;
            long total = viewRepository.count(thesisView.tid.eq(tid)); // 조회수
            item.setViewCount((int) total);
            thesisRepository.saveAndFlush(item);

        } catch (Exception e) {}
    }
}