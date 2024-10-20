package com.mallang.backend.service;

import com.mallang.backend.domain.PostEntity;
import com.mallang.backend.dto.PostDTO;
import com.mallang.backend.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//DTO -> Entity (Entity Class)
//Entity -> DTO (DTO Class)

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDTO save(PostDTO postDTO) {
        PostEntity postEntity = PostEntity.toSaveEntity(postDTO);
        postRepository.save(postEntity);
        return postDTO;
    }

    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : postEntityList) {
            postDTOList.add(PostDTO.toPostDTO(postEntity));
        }
        return postDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        postRepository.updateHits(id);
    }

    @Transactional
    public PostDTO findById(Long id) {
        Optional<PostEntity> optionalPostEntity = postRepository.findById(id);
        if (optionalPostEntity.isPresent()) {
            PostEntity postEntity = optionalPostEntity.get();
            PostDTO postDTO = PostDTO.toPostDTO(postEntity);
            return postDTO;
        } else {
            return null;
        }
    }

    public PostDTO update(PostDTO postDTO) {
        PostEntity postEntity = PostEntity.toUpdateEntity(postDTO);
        postRepository.save(postEntity);
        return findById(postDTO.getId());
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}