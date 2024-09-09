package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ThesisRepository extends JpaRepository<Thesis, Long>, QuerydslPredicateExecutor<Thesis> {
}
