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

    // 건강매거진 전체 조회
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 ID 건강매거진 조회
    public NewsDTO getNewsById(Long id) {
        return newsRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // 건강매거진 작성
    public NewsDTO createNews(NewsDTO newsDTO) {
        String mainFilePath = saveFile(newsDTO.getMainFile());
        String attachmentPath = saveFile(newsDTO.getAttachment());

        News news = convertToEntity(newsDTO);
        news.setMainFile(mainFilePath);
        news.setAttachment(attachmentPath);

        News savedNews = newsRepository.save(news);
        return convertToDTO(savedNews);
    }

    // 건강매거진 수정
    public boolean updateNewsById(Long id, NewsDTO newsDTO) {
        Optional<News> newsOptional = newsRepository.findById(id);

        if (newsOptional.isPresent()) {
            News news = newsOptional.get();

            // 필드 업데이트
            news.setTitle(newsDTO.getTitle());
            news.setContent(newsDTO.getContent());
            news.setPassword(newsDTO.getPassword());

            // 파일 업데이트
            if (newsDTO.getMainFile() != null && !newsDTO.getMainFile().isEmpty()) {
                String mainFilePath = saveFile(newsDTO.getMainFile());
                news.setMainFile(mainFilePath);
            }

            if (newsDTO.getAttachment() != null && !newsDTO.getAttachment().isEmpty()) {
                String attachmentPath = saveFile(newsDTO.getAttachment());
                news.setAttachment(attachmentPath);
            }

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

    // 파일 저장 로직
    private String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String uploadDir = "uploads/news/";
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);

                // 디렉토리 생성
                Files.createDirectories(filePath.getParent());

                // 파일 저장
                file.transferTo(filePath.toFile());

                return filePath.toString(); // 저장된 파일 경로 반환
            } catch (IOException e) {
                throw new RuntimeException("Failed to save file", e);
            }
        }
        return null; // 파일이 없는 경우
    }

    // DTO -> Entity 변환
    private News convertToEntity(NewsDTO newsDTO) {
        return News.builder()
                .title(newsDTO.getTitle())
                .newsWriter(newsDTO.getNewsWriter())
                .content(newsDTO.getContent())
                .password(newsDTO.getPassword())
                .build();
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
                .mainFile(null) // 파일 업로드가 필요 없는 조회에서는 null로 설정
                .attachment(null) // 동일하게 null 처리
                .build();
    }
}