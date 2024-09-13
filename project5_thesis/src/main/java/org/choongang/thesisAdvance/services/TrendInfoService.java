package org.choongang.thesisAdvance.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.choongang.thesis.entities.Field;
import org.choongang.thesis.entities.QField;
import org.choongang.thesis.entities.QThesisViewDaily;
import org.choongang.thesis.entities.QUserLog;
import org.choongang.thesis.repositories.FieldRepository;
import org.choongang.thesisAdvance.controllers.TrendSearch;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrendInfoService {

    private final JPAQueryFactory queryFactory;
    private final FieldRepository fieldRepository;

    public List<Map<String, Object>> getKeywordRankingByJob(TrendSearch search) {
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();
        List<String> job = search.getJob();

        QUserLog userLog = QUserLog.userLog;
        BooleanBuilder builder = new BooleanBuilder();
        if (sDate != null) {
            builder.and(userLog.searchDate.after(sDate.atStartOfDay()));
        }
        if (eDate != null) {
            builder.and(userLog.searchDate.before(eDate.atTime(LocalTime.MAX)));
        }
        if (job != null && !job.isEmpty()) {
            builder.and(userLog.job.in(job));
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

    public Map<String, Map<String, Object>> getFieldRanking(TrendSearch search) {
        LocalDate sDate = search.getSDate();
        LocalDate eDate = search.getEDate();
        if (sDate == null) {
            return null;
        }

        QThesisViewDaily daily = QThesisViewDaily.thesisViewDaily;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(daily.date.goe(sDate));

        if (eDate != null) {
            builder.and(daily.date.loe(eDate));
        }

        List<String> items = queryFactory.select(daily.fields)
                .from(daily)
                .where(builder)
                .fetch();

        if (items == null || items.isEmpty()) {
            return null;
        }

        List<String> fieldIds = items.stream().flatMap(s -> Arrays.stream(s.split(","))).distinct().toList();
        QField field = QField.field;
        List<Field> fields = (List<Field>)fieldRepository.findAll(field.id.in(fieldIds));
        Map<String, Map<String, Object>> statData = fields.stream().collect(Collectors.toMap(Field::getId, f -> {
            Map<String, Object> data = new HashMap<>();
            data.put("name", f.getName());
            data.put("subfield", f.getSubfield());
            data.put("count", 0);

            return data;
        }));

        for (String item : items) {
            for (String name : item.split(",")) {
                Map<String, Object> data = statData.get(name);
                if (data == null) {
                    data = new HashMap<>();
                    data.put("count", 1);
                } else {
                    int count = (int)data.getOrDefault("count", 0);
                    data.put("count", count + 1);
                }
                statData.put(name, data);
            }
        }

        return statData;
    }
}

