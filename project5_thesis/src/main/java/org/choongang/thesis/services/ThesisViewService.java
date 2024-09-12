package org.choongang.thesis.services;

import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.member.MemberUtil;
import org.choongang.thesis.entities.Field;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.entities.ThesisViewDaily;
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
    private final ThesisViewRepository repository;
    private final ThesisInfoService infoService;
    private final MemberUtil memberUtil;
    private final Utils utils;

    public void dailylog(Long tid) {
        try {
            Thesis item = infoService.get(tid);
            List< Field> fields = item.getFields();
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

    public void updateViewCont(Long tid) {

    }
}
