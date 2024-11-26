package com.hw.shopping.controller;

import com.hw.shopping.domain.Comment;
import com.hw.shopping.repository.CommentRepository;
import com.hw.shopping.service.CommentService;
import com.hw.shopping.service.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment")
        String postComment(@RequestParam String content,
                            @RequestParam Long parent,
        Authentication auth) {

        CustomUser customUser = (CustomUser) auth.getPrincipal();
        commentService.saveComment(customUser,content,parent);
        return "redirect:/list";
        }

}