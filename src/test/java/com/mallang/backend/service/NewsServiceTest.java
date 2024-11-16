package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class NewsServiceTest {

    @Mock
    private NewsRepository newsRepository; // mock NewsRepository

    @InjectMocks
    private NewsService newsService; // NewsService에 mock된 NewsRepository를 주입

    @Test
    void testGetAllNews() {
        // Given: 테스트 데이터 준비
        News news1 = new News("Title 1", "Content 1");
        News news2 = new News("Title 2", "Content 2");

        // mock NewsRepository가 findAll을 호출할 때 위 뉴스 데이터를 반환하도록 설정
        when(newsRepository.findAll()).thenReturn(Arrays.asList(news1, news2));

        // When: 서비스 메서드 호출
        List<NewsDTO> newsDTOList = newsService.getAllNews();

        // Then: 반환된 리스트에 대한 검증
        assertNotNull(newsDTOList); // 반환값이 null이 아님을 확인
        assertEquals(2, newsDTOList.size()); // 리스트에 두 개의 NewsDTO가 있어야 함

        // 첫 번째 NewsDTO 검증
        NewsDTO newsDTO1 = newsDTOList.get(0);
        assertEquals("Title 1", newsDTO1.getTitle()); // 제목 검증
        assertEquals("Content 1", newsDTO1.getContent()); // 내용 검증

        // 두 번째 NewsDTO 검증
        NewsDTO newsDTO2 = newsDTOList.get(1);
        assertEquals("Title 2", newsDTO2.getTitle()); // 제목 검증
        assertEquals("Content 2", newsDTO2.getContent()); // 내용 검증

        // Verify: mock 메서드가 호출되었는지 확인
        verify(newsRepository, times(1)).findAll(); // findAll() 메서드가 정확히 한 번 호출되었는지 확인
    }

    @Test
    void testCreateNews() {
        // Given: 뉴스 생성에 필요한 DTO 준비
        NewsDTO newsDTO = new NewsDTO(null, "New Title", "New Content");

        // NewsDTO를 News 엔티티로 변환 후 mock 뉴스 데이터 반환 설정
        News savedNews = new News("New Title", "New Content");
        savedNews.setId(1L); // 저장 후 ID 값 설정

        // mock NewsRepository가 save 메서드를 호출할 때 저장된 뉴스 객체를 반환하도록 설정
        when(newsRepository.save(any(News.class))).thenReturn(savedNews);

        // When: 서비스 메서드 호출
        NewsDTO createdNewsDTO = newsService.createNews(newsDTO);

        // Then: 반환된 NewsDTO 검증
        assertNotNull(createdNewsDTO); // 반환값이 null이 아님을 확인
        assertEquals(1L, createdNewsDTO.getId()); // ID 검증
        assertEquals("New Title", createdNewsDTO.getTitle()); // 제목 검증
        assertEquals("New Content", createdNewsDTO.getContent()); // 내용 검증

        // Verify: mock 메서드가 호출되었는지 확인
        verify(newsRepository, times(1)).save(any(News.class)); // save() 메서드가 정확히 한 번 호출되었는지 확인
    }
}