package com.hw.shopping.controller;

import com.hw.shopping.domain.notice;
import com.hw.shopping.repository.NoticeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice")
    String list(Model model) {
        List<notice> result = noticeRepository.findAll();
        model.addAttribute("notice", result);

        return "notice/noticeList.html";
    }
}