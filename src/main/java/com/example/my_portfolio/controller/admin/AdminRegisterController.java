package com.example.my_portfolio.controller.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.my_portfolio.entity.AdminUser;
import com.example.my_portfolio.repository.AdminUserRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/register")
@RequiredArgsConstructor
public class AdminRegisterController {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm(Model model) {
        if (adminUserRepository.count() > 0) {
            return "redirect:/admin/login";
        }
        return "admin/register";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String passcode) {
        if (adminUserRepository.count() > 0) {
            return "redirect:/admin/login";
        }

        AdminUser admin = new AdminUser();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setPasscode(passwordEncoder.encode(passcode));
        adminUserRepository.save(admin);

        return "redirect:/admin/login?registered";
    }
}