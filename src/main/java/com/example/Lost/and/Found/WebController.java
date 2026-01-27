package com.example.Lost.and.Found;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    // Головна сторінка
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Деталі речі
    @GetMapping("/item/{id}")
    public String itemDetail(@PathVariable Long id) {
        return "item-detail";
    }

    // Вхід / реєстрація
    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    // Профіль
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    // Мої оголошення
    @GetMapping("/my-items")
    public String myItems() {
        return "my-items";
    }

    // Я знайшов річ
    @GetMapping("/report/found")
    public String reportFound() {
        return "report-found";
    }

    // Я загубив річ
    @GetMapping("/report/lost")
    public String reportLost() {
        return "report-lost";
    }
}
