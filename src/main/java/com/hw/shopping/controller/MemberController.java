package com.hw.shopping.controller;

import com.hw.shopping.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/createMember")
    String MemberForm() {

        return "members/createMemberForm.html";
    }

    @PostMapping("/newMember")
    String createMember(String username, String password, String displayName) {

        memberService.join(username, password, displayName);

        return "redirect:/list";
    }

    @GetMapping("/login")
    String login() {

        return "login/login.html";
    }

    @GetMapping("/my-page")
    String myPage(Authentication auth) {
        System.out.println(auth);
        System.out.println(auth.getName());
        System.out.println(auth.isAuthenticated());
        return "member/myPage.html";
    }

}