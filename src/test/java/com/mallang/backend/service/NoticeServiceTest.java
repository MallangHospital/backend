package com.mallang.backend.service;

import com.mallang.backend.domain.Notice;
import com.mallang.backend.dto.NoticeDTO;
import com.mallang.backend.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class NoticeServiceTest {

    @Mock
    private NoticeRepository noticeRepository; // mock NoticeRepository

    @InjectMocks
    private NoticeService noticeService; // NoticeService에 mock된 NoticeRepository를 주입

    @Test
    void testGetAllNotices() {
        // Given: 테스트 데이터 준비
        Notice notice1 = new Notice();
        notice1.setTitle("Notice 1");
        notice1.setContent("Content 1");

        Notice notice2 = new Notice();
        notice2.setTitle("Notice 2");
        notice2.setContent("Content 2");

        // mock NoticeRepository가 findAll을 호출할 때 위의 두 공지사항을 반환하도록 설정
        when(noticeRepository.findAll()).thenReturn(Arrays.asList(notice1, notice2));

        // When: 서비스 메서드 호출
        List<NoticeDTO> noticeDTOList = noticeService.getAllNotices();

        // Then: 반환된 리스트에 대한 검증
        assertNotNull(noticeDTOList); // 반환값이 null이 아님을 확인
        assertEquals(2, noticeDTOList.size()); // 리스트에 두 개의 NoticeDTO가 있어야 함

        // 첫 번째 NoticeDTO 검증
        NoticeDTO noticeDTO1 = noticeDTOList.get(0);
        assertEquals("Notice 1", noticeDTO1.getTitle()); // 제목 검증
        assertEquals("Content 1", noticeDTO1.getContent()); // 내용 검증

        // 두 번째 NoticeDTO 검증
        NoticeDTO noticeDTO2 = noticeDTOList.get(1);
        assertEquals("Notice 2", noticeDTO2.getTitle()); // 제목 검증
        assertEquals("Content 2", noticeDTO2.getContent()); // 내용 검증

        // Verify: mock 메서드가 호출되었는지 확인
        verify(noticeRepository, times(1)).findAll(); // findAll() 메서드가 정확히 한 번 호출되었는지 확인
    }

    @Test
    void testCreateNotice() {
        // Given: NoticeDTO 준비
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setTitle("New Notice");
        noticeDTO.setContent("New Content");

        // Notice 엔티티를 생성하고 ID를 1L로 설정
        Notice savedNotice = new Notice();
        savedNotice.setId(1L);
        savedNotice.setTitle("New Notice");
        savedNotice.setContent("New Content");

        // mock NoticeRepository가 save 메서드를 호출할 때 위 뉴스 엔티티를 반환하도록 설정
        when(noticeRepository.save(any(Notice.class))).thenReturn(savedNotice);

        // When: 서비스 메서드 호출
        NoticeDTO createdNoticeDTO = noticeService.createNotice(noticeDTO);

        // Then: 반환된 NoticeDTO 검증
        assertNotNull(createdNoticeDTO); // 반환값이 null이 아님을 확인
        assertEquals(1L, createdNoticeDTO.getId()); // ID 검증
        assertEquals("New Notice", createdNoticeDTO.getTitle()); // 제목 검증
        assertEquals("New Content", createdNoticeDTO.getContent()); // 내용 검증

        // Verify: mock 메서드가 호출되었는지 확인
        verify(noticeRepository, times(1)).save(any(Notice.class)); // save() 메서드가 정확히 한 번 호출되었는지 확인
    }
}