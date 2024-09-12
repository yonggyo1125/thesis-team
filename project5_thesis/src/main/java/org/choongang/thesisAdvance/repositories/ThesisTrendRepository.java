package org.choongang.thesisAdvance.repositories;

import org.choongang.thesis.entities.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ThesisTrendRepository extends JpaRepository<Thesis, Long>, QuerydslPredicateExecutor<Thesis> {
    //List<Thesis> findKeywordsByGroup(String job, LocalDate startDate, LocalDate endDate);
}
