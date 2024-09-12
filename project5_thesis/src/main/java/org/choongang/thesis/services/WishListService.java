package org.choongang.thesis.services;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.choongang.global.Utils;
import org.choongang.global.exceptions.BadRequestException;
import org.choongang.member.MemberUtil;
import org.choongang.thesis.entities.QWishList;
import org.choongang.thesis.entities.WishList;
import org.choongang.thesis.entities.WishListId;
import org.choongang.thesis.repositories.WishListRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final MemberUtil memberUtil;
    private final WishListRepository wishListRepository;
    private final Utils utils;

    public void add(Long tid){
        if(!memberUtil.isLogin()){
            throw new BadRequestException(utils.getMessage("Login.Required"));
        }
        WishList wishList = WishList.builder()
                .tid(tid)
                .email(memberUtil.getMember().getEmail())
                .build();
        System.out.println("wishList : "+wishList);
        wishListRepository.saveAndFlush(wishList);
    }

    public void remove(Long tid){
        if(!memberUtil.isLogin()){
            throw new BadRequestException(utils.getMessage("Login.Required"));
        }
        WishListId wishListId = new WishListId(tid,memberUtil.getMember().getEmail());
        wishListRepository.deleteById(wishListId);
        wishListRepository.flush();
        System.out.println("위시리스트 논문 삭제");
    }

    public List<Long> getList(){
        BooleanBuilder builder = new BooleanBuilder();
        QWishList wishList = QWishList.wishList;
        builder.and(wishList.email.eq("testuser1@email.com"));

        List<Long> items = ((List<WishList>)wishListRepository.findAll(builder, Sort.by(desc("createdAt")))).stream().map(WishList::getTid).toList();

        return items;
    }

}
