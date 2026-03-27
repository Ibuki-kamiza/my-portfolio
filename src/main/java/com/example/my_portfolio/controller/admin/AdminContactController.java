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

    @PostMapping("/{id}/read")
    public String markAsRead(@PathVariable Long id) {
        contactRepository.findById(id).ifPresent(contact -> {
            contact.setIsRead(true);
            contactRepository.save(contact);
        });
        return "redirect:/admin/contacts";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        contactRepository.deleteById(id);
        return "redirect:/admin/contacts";
    }
}