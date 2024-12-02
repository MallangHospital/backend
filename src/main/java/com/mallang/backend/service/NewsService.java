package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    // 모든 뉴스 조회
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 뉴스 조회
    public NewsDTO getNewsById(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        return newsOptional.map(this::convertToDTO).orElse(null);
    }

    // 뉴스 등록
    public NewsDTO createNews(NewsDTO newsDTO, String mainFileBase64, String attachmentBase64) {
        News news = convertToEntity(newsDTO);
        news.setMainFile(mainFileBase64);
        news.setAttachment(attachmentBase64);
        News savedNews = newsRepository.save(news);
        return convertToDTO(savedNews);
    }

    // 뉴스 수정
    public boolean updateNewsById(Long id, NewsDTO newsDTO, String mainFileBase64, String attachmentBase64) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            return false;
        }

        News news = newsOptional.get();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setPassword(newsDTO.getPassword());
        news.setNewsWriter(newsDTO.getNewsWriter());
        if (mainFileBase64 != null) {
            news.setMainFile(mainFileBase64);
        }
        if (attachmentBase64 != null) {
            news.setAttachment(attachmentBase64);
        }
        newsRepository.save(news);
        return true;
    }

    // 뉴스 삭제
    public boolean deleteNewsById(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            return false;
        }
        newsRepository.deleteById(id);
        return true;
    }

    // 엔티티를 DTO로 변환
    private NewsDTO convertToDTO(News news) {
        return NewsDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .newsWriter(news.getNewsWriter())
                .password(news.getPassword())
                .content(news.getContent())
                .mainFileBase64(news.getMainFile())
                .attachmentBase64(news.getAttachment())
                .regDate(news.getRegDate() != null ? news.getRegDate().toString() : null)
                .build();
    }

    // DTO를 엔티티로 변환
    private News convertToEntity(NewsDTO newsDTO) {
        return News.builder()
                .title(newsDTO.getTitle())
                .newsWriter(newsDTO.getNewsWriter())
                .password(newsDTO.getPassword())
                .content(newsDTO.getContent())
                .build();
    }
}