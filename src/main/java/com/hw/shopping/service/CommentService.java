package com.hw.shopping.service;

import com.hw.shopping.domain.Comment;
import com.hw.shopping.domain.Item;
import com.hw.shopping.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


    public void saveComment(CustomUser customUser, String content, Long parentId) {

        String username = customUser.getUsername();

        Comment comment = new Comment();
        comment.saveComment(username, content, parentId);

        commentRepository.save(comment);
    }

    public List<Comment> commentList(Long parentId) {

        return commentRepository.findAllByParentId(parentId);
    }

    public Page<Comment> commetlistPage(Long parentId, int num){
        System.out.println("########### Before Service Call ###########");
        System.out.println("parent_id = " + parentId); // Controller parameter
        System.out.println("comment = " + num); // Page number

        Pageable pageable = PageRequest.of(num - 1 , 5);
        System.out.println("######SERVICE");
        System.out.println("pageable = " + pageable);
        System.out.println("parent_id = " + parentId);
        System.out.println("num = " + num);
        System.out.println("######SERVICE");
        return  commentRepository.findByParentId(parentId, pageable);
    }

}