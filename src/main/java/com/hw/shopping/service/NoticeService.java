package com.hw.shopping.service;

import com.hw.shopping.domain.notice;
import com.hw.shopping.repository.NoticeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public List<notice> allNotice() {
       return noticeRepository.findAll();
    }
}