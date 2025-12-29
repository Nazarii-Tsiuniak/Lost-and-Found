package com.example.Lost.and.Found;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Головна сторінка
    @GetMapping("/")
    public String index(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            // якщо користувач не залогінений → показуємо auth.html
            return "auth";
        }
        return "index"; // залогінений → index.html
    }

    @GetMapping("/item/1")
    public String itemDetail() {
        return "item-detail";
    }

    @GetMapping("/report")
    public String reportFound() {
        return "report";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    @GetMapping("/my-items")
    public String myItems() {
        return "profile";
    }
}
