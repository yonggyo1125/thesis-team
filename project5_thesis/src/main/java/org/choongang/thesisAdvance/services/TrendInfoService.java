package org.choongang.thesisAdvance.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.choongang.thesis.entities.QUserLog;
import org.choongang.thesisAdvance.controllers.TrendSearch;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TrendInfoService {

    private final JPAQueryFactory queryFactory;

    public List<Map<String, Object>> getKeywordRankingByJob(TrendSearch search) {
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();
        QUserLog userLog = QUserLog.userLog;

        BooleanBuilder builder = new BooleanBuilder();
        if (sDate != null) {
            builder.and(userLog.searchDate.after(sDate.atStartOfDay()));
        }

        if (eDate != null) {
            builder.and(userLog.searchDate.before(eDate.atTime(LocalTime.MAX)));
        }

        List<Tuple> items = queryFactory.select(userLog.job, userLog.search.count(), userLog.search)
                .from(userLog)
                .groupBy(userLog.job, userLog.search)
                .orderBy(userLog.search.count().desc())
                .fetch();

        if (items != null && !items.isEmpty()) {
            return items.stream().map(t -> {
                Map<String, Object> data = new HashMap<>();
                data.put("job", t.get(userLog.job));
                data.put("count", t.get(userLog.search.count()));
                data.put("search", t.get(userLog.search));
                return data;
            }).toList();
        }

        return Collections.EMPTY_LIST;
    }
}

