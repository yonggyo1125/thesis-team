package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserLogRepository extends JpaRepository<UserLog, Long> , QuerydslPredicateExecutor<UserLog> {
    List<UserLog> findByEmailOrderBySearchDateDesc(String email);
    //List<UserLog> findByEmailInAndSearchDateBetween(List<String> emails, LocalDateTime startDate, LocalDateTime endDate);
    //List<UserLog> findByJobInSearchDateBetween(List<String> job, LocalDateTime startDate, LocalDateTime endDate);
}

