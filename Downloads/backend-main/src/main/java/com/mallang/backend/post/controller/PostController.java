package com.mallang.backend.post.controller;


import com.mallang.backend.post.dto.PostDTO;
import com.mallang.backend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save") //구분해야 에러가 안난다.
    public String save(@ModelAttribute PostDTO postDTO) {
        System.out.println("postDTO = " + postDTO);
        postService.save(postDTO);
        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<PostDTO> postDTOList = postService.findAll();
        model.addAttribute("postList", postDTOList);
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        PostDTO postDTO = postService.findById(id);
        model.addAttribute("postUpdate", postDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute PostDTO postDTO, Model model) {
        PostDTO post = postService.update(postDTO);
        model.addAttribute("post", post);
        return "detail";
//        return "redirect:/post/" + postDTO.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        postService.delete(id);
        return "redirect:/post/";
    }
}