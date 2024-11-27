package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class NewsService {

    private final NewsRepository newsRepository;

    /**
     * 모든 뉴스 가져오기
     * @return 뉴스 목록 DTO
     */
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(this::convertToDTO) // 변환 로직을 헬퍼 메서드로 분리
                .collect(Collectors.toList());
    }

    /**
     * 뉴스 생성
     *
     * @param newsDTO 생성할 뉴스 정보 DTO
     * @return 생성된 뉴스 DTO
     */
    public NewsDTO createNews(NewsDTO newsDTO) {
        // DTO를 엔티티로 변환
        News news = convertToEntity(newsDTO);

        // 데이터 저장
        news = newsRepository.save(news);

        // 저장된 엔티티를 DTO로 변환하여 반환
        return convertToDTO(news);
    }

    /**
     * News 엔티티를 NewsDTO로 변환하는 헬퍼 메서드
     * @param news News 엔티티
     * @return NewsDTO
     */
    private NewsDTO convertToDTO(News news) {
        return new NewsDTO(
                news.getId(),
                news.getTitle(),
                news.getName(),
                news.getPassword(),
                news.getAttachment1(),
                news.getAttachment2(),
                news.getContent(),
                news.getWriteDate() != null ? news.getWriteDate() : LocalDate.now() // null 방지
        );
    }

    /**
     * NewsDTO를 News 엔티티로 변환하는 헬퍼 메서드
     * @param newsDTO NewsDTO
     * @return News 엔티티
     */
    private News convertToEntity(NewsDTO newsDTO) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setName(newsDTO.getName());
        news.setPassword(newsDTO.getPassword());
        news.setAttachment1(newsDTO.getAttachment1());
        news.setAttachment2(newsDTO.getAttachment2());
        news.setContent(newsDTO.getContent());
        return news;
    }
}