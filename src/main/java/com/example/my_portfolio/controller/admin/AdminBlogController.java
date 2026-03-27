package com.example.my_portfolio.controller.admin;

import com.example.my_portfolio.entity.BlogPost;
import com.example.my_portfolio.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/blog")
@RequiredArgsConstructor
public class AdminBlogController {

    private final BlogPostRepository blogPostRepository;

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
    public String create(@ModelAttribute BlogPost post) {
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
    public String update(@PathVariable Long id, @ModelAttribute BlogPost post) {
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