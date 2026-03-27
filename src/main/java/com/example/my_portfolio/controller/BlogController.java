package com.example.my_portfolio.controller;

import com.example.my_portfolio.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogPostRepository blogPostRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("posts", blogPostRepository.findByPublishedTrueOrderByCreatedAtDesc());
        return "blog/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        blogPostRepository.findById(id)
            .ifPresent(post -> model.addAttribute("post", post));
        return "blog/show";
    }
}