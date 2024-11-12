package com.mallang.backend.controller;

import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public List<NoticeDTO> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @PostMapping
    public NoticeDTO createNotice(@RequestBody NoticeDTO noticeDTO) {
        return noticeService.createNotice(noticeDTO);
    }
}