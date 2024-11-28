package com.hw.shopping.repository;

import com.hw.shopping.domain.Comment;
import com.hw.shopping.domain.Item;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 댓글 전체 가져오기
    List<Comment> findAllByParentId(Long parentId);

    // 댓글 페이지 기능
    Page<Comment> findByParentId(Long parentId, Pageable page);
}