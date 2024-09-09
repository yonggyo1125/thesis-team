package org.choongang.thesis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Field {
    @Id
    @Column(length=40)
    private String id; // 학문별 분류 코드

    @Column(length=60, nullable = false)
    private String name; // 대 분류명

    @Column(length=60)
    private String subfield; // 중 분류명
}
