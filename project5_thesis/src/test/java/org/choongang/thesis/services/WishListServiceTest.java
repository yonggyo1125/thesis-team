package org.choongang.thesis.services;

import org.choongang.thesis.constants.ApprovalStatus;
import org.choongang.thesis.constants.Category;
import org.choongang.thesis.entities.Thesis;
import org.choongang.thesis.repositories.ThesisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback
public class WishListServiceTest {

    @Autowired
    private WishListService wishListService;

    @Autowired
    private ThesisRepository thesisRepository;

    @BeforeEach
    public void setup() {
        for (int i = 1; i <= 5; i++) {
            Thesis thesis = Thesis.builder()
                    .title("Test Thesis " + i)
                    .poster("Test Poster " + i)
                    .category(Category.DOMESTIC)
                    .gid("GID" + i)
                    .visible(true)
                    .approvalStatus(ApprovalStatus.APPROVED)
                    .userName("Test User " + i)
                    .email("testuser" + i + "@email.com")
                    .build();
            thesisRepository.save(thesis);
        }
        thesisRepository.flush();
    }

    @Test
    @DisplayName("위시리스트에 논문 추가")
    public void testAddToWishList() {
        // 논문 ID 가져오기 한개
        List<Thesis> theses = thesisRepository.findAll();
        Long thesisId = theses.get(0).getTid();

        // 논문을 위시리스트에 추가
        wishListService.add(thesisId);

        // 위시리스트에서 논문 ID 확인
        List<Long> wishList = wishListService.getList();
        assertTrue(wishList.contains(thesisId), "논문이 위시리스트에 추가되지 않았습니다.");
    }

    @Test
    @DisplayName("위시리스트에 추가한 논문 삭제")
    public void testRemoveFromWishList() {
        List<Thesis> theses = thesisRepository.findAll();
        Long thesisId = theses.get(0).getTid();

        wishListService.add(thesisId);

        // 위시리스트에서 논문을 삭제
        wishListService.remove(thesisId);

        List<Long> wishList = wishListService.getList();
        assertFalse(wishList.contains(thesisId), "논문이 위시리스트에서 삭제되지 않았습니다.");
    }

    @Test
    @DisplayName("위시리스트에서 논문 목록 조회")
    public void testGetWishList() {
        List<Thesis> theses = thesisRepository.findAll();
        Long thesisId1 = theses.get(0).getTid();
        Long thesisId2 = theses.get(1).getTid();

        wishListService.add(thesisId1);
        wishListService.add(thesisId2);

        List<Long> wishList = wishListService.getList();
        assertEquals(2, wishList.size(), "위시리스트에 추가된 논문 수가 맞지 않습니다.");
        assertTrue(wishList.contains(thesisId1), "논문 1이 위시리스트에 추가되지 않았습니다.");
        assertTrue(wishList.contains(thesisId2), "논문 2가 위시리스트에 추가되지 않았습니다.");
    }

    /*

    @Test
    public void testGetWishListData() {
        // 논문 ID 가져오기 (가짜 데이터 중 일부를 사용)
        List<Thesis> theses = thesisRepository.findAll();
        Long thesisId1 = theses.get(0).getTid();
        Long thesisId2 = theses.get(1).getTid();

        // 위시리스트에 논문 추가
        wishListService.add(thesisId1);
        wishListService.add(thesisId2);

        // ThesisSearch 객체를 사용하여 위시리스트 논문 목록 조회
        ThesisSearch search = new ThesisSearch();
        ListData<Thesis> wishListData = thesisInfoService.getWishList(search);

        // 위시리스트에서 논문 데이터 확인
        assertEquals(2, wishListData.getItems().size(), "위시리스트에서 조회된 논문 수가 맞지 않습니다.");
        assertEquals("Test Thesis 1", wishListData.getItems().get(0).getTitle(), "첫 번째 논문 제목이 일치하지 않습니다.");
        assertEquals("Test Thesis 2", wishListData.getItems().get(1).getTitle(), "두 번째 논문 제목이 일치하지 않습니다.");
    }
    */
}
