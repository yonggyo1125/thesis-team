package org.choongang.thesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.global.entities.BaseMemberEntity;

@Entity
@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class VersionLog extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long logSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tid")
    private Thesis thesis;
    private int major; //주버전
    private int minor; //부버전

    @Lob
    private String before;

    @Lob
    private String after;

}
