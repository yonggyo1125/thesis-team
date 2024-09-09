package org.choongang.thesis.repositories;

import org.choongang.thesis.entities.WishList;
import org.choongang.thesis.entities.WishListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface WishListRepository extends JpaRepository<WishList, WishListId>, QuerydslPredicateExecutor<WishList> {
}
