package org.choongang.thesis.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Entity
public class Field {
    @Id
    @Column(length=40)
    private String id; // 학문별 분류 코드

    @Column(length=60, nullable = false)
    private String name; // 대 분류명

    @Column(length=60)
    private String subfield; // 중 분류명

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "fields", fetch = FetchType.LAZY)
    private List<Thesis> theses;
}
