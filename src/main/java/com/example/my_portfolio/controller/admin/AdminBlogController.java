package com.example.my_portfolio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.my_portfolio.entity.BlogPost;
import com.example.my_portfolio.repository.BlogPostRepository;
import com.example.my_portfolio.service.FileUploadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/blog")
@RequiredArgsConstructor
public class AdminBlogController {

    private final BlogPostRepository blogPostRepository;
    private final FileUploadService fileUploadService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("posts", blogPostRepository.findAll());
        return "admin/blog/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("post", new BlogPost());
        return "admin/blog/form";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute BlogPost post,
                         @RequestParam(required = false) MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileUploadService.uploadBlogImage(imageFile);
                if (imageUrl != null) post.setImageUrl(imageUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        post.setPublished(false);
        blogPostRepository.save(post);
        return "redirect:/admin/blog";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        blogPostRepository.findById(id)
            .ifPresent(post -> model.addAttribute("post", post));
        return "admin/blog/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute BlogPost post,
                         @RequestParam(required = false) MultipartFile imageFile) {
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = fileUploadService.uploadBlogImage(imageFile);
                if (imageUrl != null) {
                    post.setImageUrl(imageUrl);
                }
            } else {
                blogPostRepository.findById(id).ifPresent(existing -> {
                    if (post.getImageUrl() == null || post.getImageUrl().isEmpty()) {
                        post.setImageUrl(existing.getImageUrl());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        post.setId(id);
        blogPostRepository.save(post);
        return "redirect:/admin/blog";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        blogPostRepository.deleteById(id);
        return "redirect:/admin/blog";
    }
}