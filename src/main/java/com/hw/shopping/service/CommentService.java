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

    public List<Comment> commentList(Long parent_id) {

        return commentRepository.findAllByParentId(parent_id);
    }

    public Page<Comment> listPage(Long parentId, int num){
        Pageable pageable = PageRequest.of(num - 1, 5);
      return  commentRepository.findPageBy(parentId, pageable);
    }

}