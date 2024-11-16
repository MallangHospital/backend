package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class NewsService {

    private final NewsRepository newsRepository;

    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(news -> new NewsDTO(news.getId(), news.getTitle(), news.getContent()))
                .collect(Collectors.toList());
    }

    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = new News(newsDTO.getTitle(), newsDTO.getContent());
        news = newsRepository.save(news);
        return new NewsDTO(news.getId(), news.getTitle(), news.getContent());
    }
}