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

    // 건강매거진 전체 조회
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(news -> {
                    NewsDTO dto = new NewsDTO();
                    dto.setId(news.getId());
                    dto.setTitle(news.getTitle());
                    dto.setNewsWriter(news.getNewsWriter());
                    dto.setContent(news.getContent());
                    dto.setCreatedAt(news.getCreatedAt().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 특정 ID 건강매거진 조회
    public NewsDTO getNewsById(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            NewsDTO dto = new NewsDTO();
            dto.setId(news.getId());
            dto.setTitle(news.getTitle());
            dto.setNewsWriter(news.getNewsWriter());
            dto.setContent(news.getContent());
            dto.setCreatedAt(news.getCreatedAt().toString());
            return dto;
        }
        return null;
    }

    // 건강매거진 작성
    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setNewsWriter(newsDTO.getNewsWriter());
        news.setContent(newsDTO.getContent());
        news.setPassword(newsDTO.getPassword());

        News savedNews = newsRepository.save(news);

        NewsDTO dto = new NewsDTO();
        dto.setId(savedNews.getId());
        dto.setTitle(savedNews.getTitle());
        dto.setNewsWriter(savedNews.getNewsWriter());
        dto.setContent(savedNews.getContent());
        dto.setCreatedAt(savedNews.getCreatedAt().toString());
        return dto;
    }

    // 건강매거진 수정
    public boolean updateNewsById(Long id, NewsDTO newsDTO) {
        Optional<News> newsOptional = newsRepository.findById(id);

        if (newsOptional.isPresent()) {
            News news = newsOptional.get();
            news.setTitle(newsDTO.getTitle());
            news.setContent(newsDTO.getContent());
            news.setPassword(newsDTO.getPassword());
            newsRepository.save(news);
            return true;
        }

        return false; // 수정할 건강매거진이 존재하지 않을 경우
    }

    // 건강매거진 삭제
    public boolean deleteNewsById(Long id) {
        if (newsRepository.existsById(id)) {
            newsRepository.deleteById(id);
            return true;
        }
        return false; // 삭제할 건강매거진이 존재하지 않을 경우
    }
}