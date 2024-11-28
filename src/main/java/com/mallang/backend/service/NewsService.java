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
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 ID 건강매거진 조회
    public NewsDTO getNewsById(Long id) {
        return newsRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    // 건강매거진 작성
    public NewsDTO createNews(NewsDTO newsDTO) {
        News news = convertToEntity(newsDTO);
        News savedNews = newsRepository.save(news);
        return convertToDTO(savedNews);
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

    // DTO -> Entity 변환
    private News convertToEntity(NewsDTO newsDTO) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setNewsWriter(newsDTO.getNewsWriter());
        news.setContent(newsDTO.getContent());
        news.setPassword(newsDTO.getPassword());
        return news;
    }

    // Entity -> DTO 변환
    private NewsDTO convertToDTO(News news) {
        return NewsDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .newsWriter(news.getNewsWriter())
                .content(news.getContent())
                .password(news.getPassword())
                .createdAt(news.getRegDate().toString())
                .build();
    }
}