package com.hw.shopping.repository;

import com.hw.shopping.domain.notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<notice,Long> {

}