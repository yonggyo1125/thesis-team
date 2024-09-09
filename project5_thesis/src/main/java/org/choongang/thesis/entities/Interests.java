package org.choongang.thesis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Interests {
    @Id
    @Column(length=30)
    private String id; // 학문별 분류 코드

    @Id
    @Column(length=80)
    private String email; // 회원 이메일 주소
}
