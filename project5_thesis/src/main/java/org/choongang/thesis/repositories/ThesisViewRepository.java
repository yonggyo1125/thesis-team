package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.ThesisView;
import org.choongang.thesis.entities.ThesisViewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ThesisViewRepository extends JpaRepository<ThesisView, ThesisViewId>, QuerydslPredicateExecutor<ThesisView> {
}
