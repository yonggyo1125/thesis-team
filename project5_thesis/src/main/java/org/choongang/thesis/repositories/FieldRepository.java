package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FieldRepository extends JpaRepository<Field, String>, QuerydslPredicateExecutor<Field> {
}
