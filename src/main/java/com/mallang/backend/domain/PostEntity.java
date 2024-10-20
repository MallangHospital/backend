package com.mallang.backend.domain;

import com.mallang.backend.dto.PostDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "post_table") //특정 테이블 이름을 따로 설정. 필수X
public class PostEntity extends BaseEntity {
    @Id // pk컬럼 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 컬럼의 크기를 설정, not null
    private String postWriter;

    @Column // 크기 255, null 가능
    private String postPass;

    @Column
    private String postTitle;

    @Column(length = 500)
    private String postContents;

    @Column
    private int postHits;

    public static PostEntity toSaveEntity(PostDTO postDTO){
        PostEntity post = new PostEntity();
        post.setPostWriter(postDTO.getPostWriter());
        post.setPostPass(postDTO.getPostPass());
        post.setPostTitle(postDTO.getPostTitle());
        post.setPostContents(postDTO.getPostContents());
        post.setPostHits(0);

        return post;
    }
    public static PostEntity toUpdateEntity(PostDTO postDTO) {
        PostEntity post = new PostEntity();
        post.setId(postDTO.getId());
        post.setPostWriter(postDTO.getPostWriter());
        post.setPostPass(postDTO.getPostPass());
        post.setPostTitle(postDTO.getPostTitle());
        post.setPostContents(postDTO.getPostContents());
        post.setPostHits(postDTO.getPostHits());
        return post;
    }
}