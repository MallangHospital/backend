package com.mallang.backend.controller;

import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }
    // 모든 공지사항 조회
    @GetMapping
    public List<NoticeDTO> getAllNotices() {
        return noticeService.getAllNotices();
    }
    // 공지사항 작성
    @PostMapping
    public NoticeDTO createNotice(@RequestBody NoticeDTO noticeDTO) {
        return noticeService.createNotice(noticeDTO);
    }
}