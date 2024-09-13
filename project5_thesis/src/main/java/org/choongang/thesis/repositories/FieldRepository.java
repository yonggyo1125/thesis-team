package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
public interface FieldRepository extends JpaRepository<Field, String> , QuerydslPredicateExecutor<Field> {
    List<Field> findByIdIn(List<String> ids);

    Field findBySubfield(String subfield); //테스트용
    Field findByName(String name); //테스트용
}
