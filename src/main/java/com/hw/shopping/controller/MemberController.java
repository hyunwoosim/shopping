package com.hw.shopping.controller;

import com.hw.shopping.repository.MemberRepository;
import com.hw.shopping.security.JwtUtil;
import com.hw.shopping.service.CustomUser;
import com.hw.shopping.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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

    @GetMapping("/myPage")
    String myPage(Authentication auth) {
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);
        System.out.println(auth.getName());
        System.out.println(auth.isAuthenticated());
        return "members/myPage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        MemberDto data = new MemberDto(result.getUsername(),result.getDisplayName());

        return data;
    }

    @PostMapping("/login/jwt")
        @ResponseBody
        public String loginJWT(@RequestBody Map<String, String> data,
        HttpServletResponse response
            ) {

        var authToken = new UsernamePasswordAuthenticationToken(data.get("username"),
                                                                data.get("password"));
        
        Authentication auth = authenticationManagerBuilder.getObject()
            .authenticate(authToken);
        
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println("Jwt = " + jwt);

        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(1000);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "";
    }

    @GetMapping("/my-page/jwt")
    @ResponseBody
    String myPageJWT(Authentication auth) {

        CustomUser user = (CustomUser) auth.getPrincipal();
        System.out.println("user = " + user);
        System.out.println("user.displayName = " + user.displayName);
        System.out.println("user.getAuthorities() = " + user.getAuthorities());

        return "";
    }


}

class MemberDto {
    public String username;
    public String displayName;
    MemberDto(String a, String b) {
        this.username = a;
        this.displayName = b;
    }
}