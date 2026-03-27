package com.example.my_portfolio.controller;

import com.example.my_portfolio.entity.Contact;
import com.example.my_portfolio.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository contactRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact";
    }

    @PostMapping
    public String submit(@ModelAttribute Contact contact) {
        contactRepository.save(contact);
        return "redirect:/contact?sent";
    }
}