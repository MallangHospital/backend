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

    // 공지사항 전체 조회
    public List<NoticeDTO> getAllNotices() {
        List<Notice> notices = noticeRepository.findAll();
        return notices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 공지사항 조회
    public NoticeDTO getNoticeById(Long id) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        return noticeOptional.map(this::convertToDTO).orElse(null);
    }

    // 공지사항 작성
    public NoticeDTO createNotice(NoticeDTO noticeDTO) {
        Notice notice = convertToEntity(noticeDTO);
        Notice savedNotice = noticeRepository.save(notice);
        return convertToDTO(savedNotice);
    }

    // 공지사항 수정
    public boolean updateNotice(Long id, NoticeDTO noticeDTO) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        if (noticeOptional.isEmpty()) {
            return false;
        }

        Notice notice = noticeOptional.get();
        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());
        notice.setPassword(noticeDTO.getPassword());
        notice.setNoticeWriter(noticeDTO.getNoticeWriter());

        noticeRepository.save(notice);
        return true;
    }

    // 공지사항 삭제
    public boolean deleteNotice(Long id, String password) {
        Optional<Notice> noticeOptional = noticeRepository.findById(id);
        if (noticeOptional.isEmpty()) {
            return false;
        }

        Notice notice = noticeOptional.get();
        if (!notice.getPassword().equals(password)) {
            return false;
        }

        noticeRepository.deleteById(id);
        return true;
    }

    // Entity → DTO 변환
    private NoticeDTO convertToDTO(Notice notice) {
        return NoticeDTO.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .noticeWriter(notice.getNoticeWriter())
                .password(notice.getPassword())
                .content(notice.getContent())
                .regDate(notice.getRegDate() != null ? notice.getRegDate().toString() : null)
                .build();
    }

    // DTO → Entity 변환
    private Notice convertToEntity(NoticeDTO noticeDTO) {
        return Notice.builder()
                .title(noticeDTO.getTitle())
                .noticeWriter(noticeDTO.getNoticeWriter())
                .password(noticeDTO.getPassword())
                .content(noticeDTO.getContent())
                .build();
    }
}