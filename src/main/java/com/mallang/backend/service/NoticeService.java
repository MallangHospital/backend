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
                .map(notice -> {
                    NoticeDTO dto = new NoticeDTO();
                    dto.setId(String.valueOf(notice.getId()));
                    dto.setTitle(notice.getTitle());
                    dto.setNoticeWriter(notice.getNoticeWriter());
                    dto.setContent(notice.getContent());
                    dto.setWriteDate(notice.getCreatedAt().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 특정 ID 공지사항 조회
    public NoticeDTO getNoticeById(Long id) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        if (noticeOptional.isPresent()) {
            Notice notice = noticeOptional.get();
            NoticeDTO dto = new NoticeDTO();
            dto.setId(String.valueOf(notice.getId()));
            dto.setTitle(notice.getTitle());
            dto.setNoticeWriter(notice.getNoticeWriter());
            dto.setContent(notice.getContent());
            dto.setWriteDate(notice.getCreatedAt().toString());
            return dto;
        }
        return null; // 공지사항이 존재하지 않을 경우
    }

    // 공지사항 작성
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = new Notice();
        notice.setTitle(noticeDTO.getTitle());
        notice.setNoticeWriter(noticeDTO.getNoticeWriter());
        notice.setPassword(noticeDTO.getPassword());
        notice.setContent(noticeDTO.getContent());

        Notice savedNotice = noticeRepository.save(notice);

        NoticeDTO dto = new NoticeDTO();
        dto.setId(String.valueOf(savedNotice.getId()));
        dto.setTitle(savedNotice.getTitle());
        dto.setNoticeWriter(savedNotice.getNoticeWriter());
        dto.setContent(savedNotice.getContent());
        dto.setWriteDate(savedNotice.getCreatedAt().toString());
        return dto;
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