package com.hw.shopping.service;

import com.hw.shopping.domain.Member;
import com.hw.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public void join(String username, String password, String displayName) {
       String hashPassword = passwordEncoder.encode(password);

       Member member = new Member();
       member.createMember(username, hashPassword, displayName);

        memberRepository.save(member);

    }
}