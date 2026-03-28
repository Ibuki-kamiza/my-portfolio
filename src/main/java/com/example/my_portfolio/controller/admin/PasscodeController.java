package com.example.my_portfolio.controller.admin;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.my_portfolio.entity.AdminUser;
import com.example.my_portfolio.repository.AdminUserRepository;
import com.example.my_portfolio.service.PasscodeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/passcode")
@RequiredArgsConstructor
public class PasscodeController {

    private final AdminUserRepository adminUserRepository;
    private final PasscodeService passcodeService;

    @GetMapping
    public String passcodeForm(Authentication authentication, Model model, HttpSession session) {
        if (authentication == null) return "redirect:/admin/login";

        AdminUser user = adminUserRepository.findByUsername(authentication.getName())
            .orElse(null);
        if (user == null) return "redirect:/admin/login";

        if (user.getPasscode() == null) {
            session.setAttribute("passcodeVerified", true);
            return "redirect:/admin/dashboard";
        }

        if (passcodeService.isLocked(user)) {
            model.addAttribute("locked", true);
            model.addAttribute("lockedUntil", user.getLockedUntil());
            return "admin/passcode";
        }

        model.addAttribute("remainingAttempts", passcodeService.getRemainingAttempts(user));
        return "admin/passcode";
    }

    @PostMapping
    public String verifyPasscode(@RequestParam String passcode,
                                  Authentication authentication,
                                  HttpSession session,
                                  Model model) {
        if (authentication == null) return "redirect:/admin/login";

        AdminUser user = adminUserRepository.findByUsername(authentication.getName())
            .orElse(null);
        if (user == null) return "redirect:/admin/login";

        if (passcodeService.isLocked(user)) {
            model.addAttribute("locked", true);
            return "admin/passcode";
        }

        if (passcodeService.verifyPasscode(user, passcode)) {
            session.setAttribute("passcodeVerified", true);
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", true);
        model.addAttribute("remainingAttempts", passcodeService.getRemainingAttempts(user));
        return "admin/passcode";
    }
}