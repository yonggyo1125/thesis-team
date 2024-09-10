package org.choongang.note.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.file.entities.FileInfo;
import org.choongang.global.entities.BaseEntity;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteData extends BaseEntity {
    @Id @GeneratedValue
    private Long noteSeq; // 노트 번호

    @JoinColumn(name="nid")
    @ManyToOne(fetch = FetchType.LAZY)
    private Note note;

    @Column(length=60)
    private String category;

    @Column(length=45, nullable = false)
    private String gid; // 그룹 ID

    @Column(nullable = false)
    private String subject; // 제목

    @Lob
    @Column(nullable = false)
    private String content; // 내용

    @Column(length=80, nullable = false)
    private String email; // 로그인 회원 이메일

    @Column(length=40, nullable = false)
    private String username; // 로그인 회원명

    @Transient
    private boolean editable; // 수정, 삭제 가능 여부

    @Transient
    private List<FileInfo> editorImages; // 에디터 이미지 
    
    @Transient
    private List<FileInfo> attachFiles; // 첨부 파일(다운로드용)
}
