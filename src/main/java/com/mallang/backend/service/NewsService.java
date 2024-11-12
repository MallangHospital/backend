
package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

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