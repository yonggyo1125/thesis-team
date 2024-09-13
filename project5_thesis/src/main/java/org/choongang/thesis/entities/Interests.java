package org.choongang.thesis.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(InterestsId.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Interests {
    @Id
    @Column(length = 65)
    private String id; //학문별 분류 코드

    @Id
    @Column(length = 80)
    private String email; // 회원 이메일 주소

}
