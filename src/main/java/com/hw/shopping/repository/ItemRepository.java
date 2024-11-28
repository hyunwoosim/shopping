package com.hw.shopping.repository;

import com.hw.shopping.domain.Item;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findPageBy(Pageable page);

    // 검색기능
    List<Item> findAllByTitleContains(String title);

    // Full text index를 사용한 검색기능( 효율증가)
    @Query(value = "select * from item where match(title) against(?1)", nativeQuery = true)
    Page<Item> rawQuery1(String text, Pageable page);
}