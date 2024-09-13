package org.choongang.thisis.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interests {
    private String id; //학문별 분류 코드
    private String email; // 회원 이메일 주소
}
