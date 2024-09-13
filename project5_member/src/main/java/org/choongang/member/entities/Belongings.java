package org.choongang.member.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(BelongingId.class)
@NoArgsConstructor
public class Belongings {

@Id
    @Column(length = 30)
    private String id; //학문별 분류 코드

    @Id
    @Column(length = 80)
    private String email; // 회원 이메일 주소
}
