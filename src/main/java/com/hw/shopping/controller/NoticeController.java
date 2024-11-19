package com.hw.shopping.controller;

import com.hw.shopping.domain.notice;
import com.hw.shopping.repository.NoticeRepository;
import com.hw.shopping.service.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/notice")
    String list(Model model) {
        List<notice> result = noticeService.allNotice();
        model.addAttribute("notice", result);

        return "notice/noticeList.html";
    }
}