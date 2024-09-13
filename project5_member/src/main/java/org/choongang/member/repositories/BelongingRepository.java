package org.choongang.member.repositories;

import org.choongang.member.entities.BelongingId;
import org.choongang.member.entities.Belongings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BelongingRepository extends JpaRepository<Belongings, BelongingId> {
    List<Belongings> findAllByEmail(String email);
    void deleteByEmail(String email);
}
