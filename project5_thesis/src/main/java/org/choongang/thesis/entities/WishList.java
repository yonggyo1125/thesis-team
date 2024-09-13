package org.choongang.thesis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.choongang.global.entities.BaseEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WishListId.class)
@EntityListeners(AuditingEntityListener.class)
public class WishList extends BaseEntity {
    @Id
    private Long tid; //논문아이디

    @Id
    @Column(length=80)
    @CreatedBy
    private String email; //회원 이메일
}
