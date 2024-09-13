package org.choongang.thesis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ThesisViewDailyId.class)
public class ThesisViewDaily { //기간 조회, 통계에 사용->하루 지나면 기록 누적
    @Id
    @Column(name="_TID")
    private Long tid;

    @Id
    @Column(name="_UID")
    private int uid;

    @Id
    @Column(name="dailyDate")
    private LocalDate date;

    @Column(length=3000)
    private String fields;
}