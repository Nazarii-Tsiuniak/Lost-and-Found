package com.example.Lost.and.Found;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    // Головна сторінка (Список речей)
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // Сторінка детальної інформації про річ
    @GetMapping("/item/1")
    public String itemDetail() {
        return "item-detail";
    }

    // Форма "Я знайшов річ"
    @GetMapping("/report")
    public String reportFound() {
        return "report";
    }

    // Особистий кабінет (Профіль)
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    // Сторінка входу та реєстрації
    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    // Мої речі (можна використовувати той самий шаблон профілю або окремий)
    @GetMapping("/my-items")
    public String myItems() {
        return "profile";
    }
}