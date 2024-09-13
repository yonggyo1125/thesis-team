package org.choongang.thesis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Data
@Entity
@IdClass(ThesisViewId.class)
public class ThesisView { //유니크한 뷰, 한번 읽은 논문은 더이상 조회수가 올라가지 않게 함
    @Id
    @Column(name="_TID")
    private Long tid;

    @Id
    @Column(name="_UID")
    private int uid;
}