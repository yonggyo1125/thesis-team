package org.choongang.thesis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Data
@Entity
@IdClass(ThesisViewId.class)
public class ThesisView {
    @Id
    private Long tid;

    @Id
    @Column(name="_UID")
    private int uid;
}
