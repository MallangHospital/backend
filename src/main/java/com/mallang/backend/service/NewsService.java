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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;
    private final S3UploaderService s3UploaderService; // S3 업로드 서비스 추가


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

        // S3에 파일 업로드
        if (mainFile != null && !mainFile.isEmpty()) {
            news.setMainFile(uploadFileToS3(mainFile, "news/main"));
        }
        if (attachment != null && !attachment.isEmpty()) {
            news.setAttachment(uploadFileToS3(attachment, "news/attachments"));
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

        // S3에 파일 업데이트
        if (mainFile != null && !mainFile.isEmpty()) {
            if (news.getMainFile() != null) {
                deleteFileFromS3(news.getMainFile()); // 기존 파일 삭제
            }
            news.setMainFile(uploadFileToS3(mainFile, "news/main"));
        }
        if (attachment != null && !attachment.isEmpty()) {
            if (news.getAttachment() != null) {
                deleteFileFromS3(news.getAttachment()); // 기존 파일 삭제
            }
            news.setAttachment(uploadFileToS3(attachment, "news/attachments"));
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

        News news = newsOptional.get();

        // S3에 저장된 파일 삭제
        if (news.getMainFile() != null) {
            deleteFileFromS3(news.getMainFile());
        }
        if (news.getAttachment() != null) {
            deleteFileFromS3(news.getAttachment());
        }

        newsRepository.delete(news);
        return true;
    }

    // S3에 파일 업로드
    private String uploadFileToS3(MultipartFile file, String dirName) {
        try {
            return s3UploaderService.upload(file, dirName);
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 실패: " + e.getMessage(), e);
        }
    }

    // S3에서 파일 삭제
    private void deleteFileFromS3(String fileUrl) {
        try {
            s3UploaderService.delete(fileUrl);
        } catch (Exception e) {
            throw new RuntimeException("S3 파일 삭제 실패: " + e.getMessage(), e);
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
                .mainFile(news.getMainFile()) // S3 URL 반환
                .attachment(news.getAttachment()) // S3 URL 반환
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