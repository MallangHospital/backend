package com.mallang.backend.service;

import com.mallang.backend.domain.Notice;
import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;

    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(notice -> new NoticeDTO(notice.getId(), notice.getTitle(), notice.getContent()))
                .collect(Collectors.toList());
    }

    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice(noticeDTO.getTitle(), noticeDTO.getContent());
        notice = noticeRepository.save(notice);
        return new NoticeDTO(notice.getId(), notice.getTitle(), notice.getContent());
    }
}