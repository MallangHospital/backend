package com.mallang.backend.service;

import com.mallang.backend.domain.Notice;
import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 모든 공지사항 조회
    public List<NoticeDTO> getAllNotices() {
        return noticeRepository.findAll().stream()
                .map(notice -> NoticeDTO.builder()
                        .id(String.valueOf(notice.getId()))
                        .title(notice.getTitle())
                        .noticeWriter(notice.getNoticeWriter())
                        .content(notice.getContent())
                        .writeDate(notice.getRegDate().toString())
                        .build())
                .collect(Collectors.toList());
    }

    // 특정 ID 공지사항 조회
    public NoticeDTO getNoticeById(Long id) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        return noticeOptional.map(notice -> NoticeDTO.builder()
                .id(String.valueOf(notice.getId()))
                .title(notice.getTitle())
                .noticeWriter(notice.getNoticeWriter())
                .content(notice.getContent())
                .writeDate(notice.getRegDate().toString())
                .build()).orElse(null);
    }

    // 공지사항 작성
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = Notice.builder()
                .title(noticeDTO.getTitle())
                .noticeWriter(noticeDTO.getNoticeWriter())
                .password(noticeDTO.getPassword())
                .content(noticeDTO.getContent())
                .build();

        Notice savedNotice = noticeRepository.save(notice);

        return NoticeDTO.builder()
                .id(String.valueOf(savedNotice.getId()))
                .title(savedNotice.getTitle())
                .noticeWriter(savedNotice.getNoticeWriter())
                .content(savedNotice.getContent())
                .writeDate(savedNotice.getRegDate().toString())
                .build();
    }

    // 공지사항 삭제
    public boolean deleteNoticeById(Long id) {
        if (noticeRepository.existsById(id)) {
            noticeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 공지사항 수정
    public boolean updateNoticeById(Long id, NoticeDTO noticeDTO) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);

        if (noticeOptional.isPresent()) {
            Notice notice = noticeOptional.get();
            notice.setTitle(noticeDTO.getTitle());
            notice.setContent(noticeDTO.getContent());
            notice.setPassword(noticeDTO.getPassword());
            noticeRepository.save(notice);
            return true;
        }

        return false; // 수정할 공지사항이 존재하지 않을 경우
    }
}