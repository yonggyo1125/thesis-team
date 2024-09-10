package org.choongang.thesis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.global.entities.BaseMemberEntity;
import org.choongang.thesis.constants.Category;

import java.util.List;
import java.util.Map;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Thesis extends BaseMemberEntity {
    @Id @GeneratedValue
    private Long tid;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Field> fields;

    @Column(length=40, nullable = false)
    private String poster; // 편집자

    @Column(nullable = false)
    private String title; // 제목

    private String contributor; // 기여자

    @Lob
    private String thAbstract; // 초록

    @Lob
    private String reference; // 참고 문헌

    @Column(length=65, nullable = false)
    private String gid; // 그룹 ID
    private boolean visible; //  공개 여부
    private boolean approval; // 승인 여부

    private int viewCount; // 조회수

    @Column(length=80)
    private String publisher; // 발행기관

    @Lob
    private String toc; // 목차

    @Column(length=20)
    private String language; // 언어

    @Column(length=40)
    private String country; // 국가

    @Column(length=40, nullable = false)
    private String userName; // 회원명

    @Column(length=80, nullable = false)
    private String email; // 이메일

    @Transient
    private FileInfo fileInfo;

    @Transient
    private String _category; // 문자열 분류명

    @Transient
    private Map<String, String[]> _fields; // 학문 분류, id, name, subfield
}
