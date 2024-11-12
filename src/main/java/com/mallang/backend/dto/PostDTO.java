package com.mallang.backend.dto;


import com.mallang.backend.domain.Role;
import lombok.*;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개 변수로 하는 생성자
public class PostDTO {
    private Long id;
    private String postWriter;
    private String postPass;
    private String postTitle;
    private String postContents;
    private int postHits;
    private LocalDateTime postCreatedTime; //글 작성 시간
    private LocalDateTime postUpdatedTime;

    public static PostDTO toPostDTO(Role.PostEntity postEntity){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setPostWriter(postEntity.getPostWriter());
        postDTO.setPostPass(postEntity.getPostPass());
        postDTO.setPostTitle(postEntity.getPostTitle());
        postDTO.setPostContents(postEntity.getPostContents());
        postDTO.setPostHits(postEntity.getPostHits());

        return postDTO;

    }
}