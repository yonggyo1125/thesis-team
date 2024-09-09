package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserLogRepository extends JpaRepository<UserLog, Long>, QuerydslPredicateExecutor<UserLog> {
}
