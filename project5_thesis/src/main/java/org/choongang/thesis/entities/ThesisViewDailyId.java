package org.choongang.thesis.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ThesisViewDailyId {
    private Long tid;
    private int uid;
    private LocalDate date;
}