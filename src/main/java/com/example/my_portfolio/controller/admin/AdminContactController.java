package com.example.my_portfolio.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.my_portfolio.repository.ContactRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/contacts")
@RequiredArgsConstructor
public class AdminContactController {

    private final ContactRepository contactRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("contacts", contactRepository.findAllByOrderByCreatedAtDesc());
        return "admin/contacts/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        contactRepository.findById(id)
            .ifPresent(contact -> model.addAttribute("contact", contact));
        return "admin/contacts/show";
    }

    @PostMapping("/{id}/toggle-read")
    public String toggleRead(@PathVariable Long id) {
        contactRepository.findById(id).ifPresent(contact -> {
            contact.setIsRead(!contact.getIsRead());
            contactRepository.save(contact);
        });
        return "redirect:/admin/contacts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        contactRepository.deleteById(id);
        return "redirect:/admin/contacts";
    }
}