package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.Interests;
import org.choongang.thesis.entities.InterestsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface InterestsRepository extends JpaRepository<Interests, InterestsId>, QuerydslPredicateExecutor<Interests> {
}
