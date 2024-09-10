package org.choongang.note.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.global.entities.BaseMemberEntity;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note extends BaseMemberEntity {
    @Id
    @Column(length=40)
    private String nid;

    @Column(length=90, nullable = false)
    private String nName;

    private int rowsPerPage; // 한페이지당 노트 갯수
    private int pageCount; // 페이징 갯수

    @Column(length=10)
    private String locationAfterWriting; // 글 작성 이후 이동할 경로(list, view)

    @Column(length=20)
    private String skin; // 스킨

    @Lob
    private String category; // 분류
    private boolean active; // 사용 여부
}
