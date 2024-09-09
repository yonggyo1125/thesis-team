package org.choongang.thesis.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.choongang.global.entities.BaseEntity;

@Data
@Entity
public class WishList extends BaseEntity {
    @Id
    private Long tid; // 논문 아이디

    @Id
    @Column(length=80)
    private String email; // 회원 이메일
}
