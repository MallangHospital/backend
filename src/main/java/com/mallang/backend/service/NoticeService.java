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

    private final NoticeRepository noticeRepository;

    @Autowired
    public NoticeService(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(notice -> {
                    NoticeDTO dto = new NoticeDTO(); // 기본 생성자 사용
                    dto.setId(notice.getId());  // Setter 사용
                    dto.setTitle(notice.getTitle());
                    dto.setContent(notice.getContent());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice(); // 기본 생성자 사용
        notice.setTitle(noticeDTO.getTitle());  // Setter 사용
        notice.setContent(noticeDTO.getContent());
        notice = noticeRepository.save(notice);

        // 반환할 때도 기본 생성자 사용 후 Setter로 값 세팅
        NoticeDTO dto = new NoticeDTO();
        dto.setId(notice.getId());
        dto.setTitle(notice.getTitle());
        dto.setContent(notice.getContent());
        return dto;
    }
}