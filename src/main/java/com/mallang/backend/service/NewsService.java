package com.mallang.backend.service;

import com.mallang.backend.domain.News;
import com.mallang.backend.dto.NewsDTO;
import com.mallang.backend.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    // 모든 건강매거진 조회
    public List<NewsDTO> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        return newsList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 특정 건강매거진 조회
    public NewsDTO getNewsById(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        return newsOptional.map(this::convertToDTO).orElse(null);
    }

    // 건강매거진 작성
    public NewsDTO createNews(NewsDTO newsDTO, MultipartFile mainFile, MultipartFile attachment) {
        News news = convertToEntity(newsDTO);

        // 파일 저장 처리
        if (mainFile != null && !mainFile.isEmpty()) {
            news.setMainFile(saveFile(mainFile));
        }
        if (attachment != null && !attachment.isEmpty()) {
            news.setAttachment(saveFile(attachment));
        }

        News savedNews = newsRepository.save(news);
        return convertToDTO(savedNews);
    }

    // 건강매거진 수정
    public boolean updateNewsById(Long id, NewsDTO newsDTO, MultipartFile mainFile, MultipartFile attachment) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            return false;
        }

        News news = newsOptional.get();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setPassword(newsDTO.getPassword());
        news.setNewsWriter(newsDTO.getNewsWriter());

        // 파일 업데이트 처리
        if (mainFile != null && !mainFile.isEmpty()) {
            news.setMainFile(saveFile(mainFile));
        }
        if (attachment != null && !attachment.isEmpty()) {
            news.setAttachment(saveFile(attachment));
        }

        newsRepository.save(news);
        return true;
    }

    // 건강매거진 삭제
    public boolean deleteNewsById(Long id) {
        Optional<News> newsOptional = newsRepository.findById(id);
        if (newsOptional.isEmpty()) {
            return false;
        }
        newsRepository.deleteById(id);
        return true;
    }

    // 파일 저장 로직
    private String saveFile(MultipartFile file) {
        try {
            String uploadDir = "uploads/news/";
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            // 디렉토리 생성
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + e.getMessage(), e);
        }
    }

    // 엔티티를 DTO로 변환
    private NewsDTO convertToDTO(News news) {
        return NewsDTO.builder()
                .id(news.getId())
                .title(news.getTitle())
                .newsWriter(news.getNewsWriter())
                .password(news.getPassword())
                .content(news.getContent())
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