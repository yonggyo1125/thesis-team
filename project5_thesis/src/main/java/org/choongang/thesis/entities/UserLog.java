package org.choongang.thesis.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserLog {
    @Id @GeneratedValue
    private Long logSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tid")
    private Thesis thesis;

    @CreatedBy
    @Column(length=80, nullable = false)
    private String email;

    @Column(length=80, nullable = false)
    private String search; // 검색어

    @CreatedDate
    private LocalDateTime searchDate; // 검색일
}
