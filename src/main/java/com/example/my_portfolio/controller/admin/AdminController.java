package com.example.my_portfolio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_portfolio.repository.BlogPostRepository;
import com.example.my_portfolio.repository.ContactRepository;
import com.example.my_portfolio.repository.WorkRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WorkRepository workRepository;
    private final BlogPostRepository blogPostRepository;
    private final ContactRepository contactRepository;

    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("workCount", workRepository.count());
        model.addAttribute("blogCount", blogPostRepository.count());
        model.addAttribute("contactCount", contactRepository.count());
        model.addAttribute("unreadCount", contactRepository.countByIsReadFalse());
        return "admin/dashboard";
    }
}