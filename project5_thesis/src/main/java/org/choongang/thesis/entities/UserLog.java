package org.choongang.thesis.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserLog {
    @Id @GeneratedValue
    private Long logSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tid")
    @JsonIgnore
    private Thesis thesis;

    @CreatedBy
    @Column(length=80, nullable = false)
    private String email;

    @Column(length=10, nullable = false)
    private String job; // 검색시 직업도 추가!

    @Column(length=80)
    private String search; // 검색어

    @CreatedDate
    private LocalDateTime searchDate; // 검색일
}
