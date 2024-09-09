package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.VersionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface VersionLogRepository extends JpaRepository<VersionLog, Long>, QuerydslPredicateExecutor<VersionLog> {
}
