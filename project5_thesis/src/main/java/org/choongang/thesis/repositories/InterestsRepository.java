package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.Interests;
import org.choongang.thesis.entities.InterestsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestsRepository extends JpaRepository<Interests, InterestsId>, QuerydslPredicateExecutor<Interests> {
    List<Interests> findAllByEmail(String email);

    @Query("SELECT I.id FROM Interests I WHERE I.email = :email")
    List<String> findIdsByEmail(@Param("email") String email);
    void deleteByEmail(String email);
}
