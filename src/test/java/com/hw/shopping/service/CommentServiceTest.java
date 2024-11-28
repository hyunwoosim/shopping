package com.hw.shopping.service;

import com.hw.shopping.domain.Comment;
import com.hw.shopping.repository.CommentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class CommentServiceTest {

    @Autowired
    private  CommentRepository commentRepository;


    @Test
    @Transactional
        public void testFindPageByWithData() {
            Comment comment1 = new Comment();
            comment1.setParentId(2L);
            comment1.setContent("Test Comment 1");
            commentRepository.save(comment1);

            Pageable pageable = PageRequest.of(0, 5);
            Page<Comment> comments = commentRepository.findByParentId(2L, pageable);

            System.out.println("Comments: " + comments.getContent());
    }


}