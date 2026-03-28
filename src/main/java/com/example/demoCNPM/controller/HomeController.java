package com.example.demoCNPM.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String redirectAfterLogin(Authentication auth) {

        if (auth == null) {
            return "redirect:/login";
        }

        boolean isTechnical = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TECHNICAL"));

        if (isTechnical) {
            return "redirect:/technical/home";
        }

        boolean isReception = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_RECEPTION"));

        if (isReception) {
            return "redirect:/reception/home";
        }

        return "redirect:/login";
    }
}
