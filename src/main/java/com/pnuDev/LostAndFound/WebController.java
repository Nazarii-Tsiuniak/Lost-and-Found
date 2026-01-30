package com.pnuDev.LostAndFound;

import com.pnuDev.LostAndFound.model.Item;
import com.pnuDev.LostAndFound.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@Controller
public class WebController {

    private final ItemRepository itemRepository;

    public WebController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // ЗАЛИШТЕ ТІЛЬКИ ЦЕЙ МЕТОД ДЛЯ "/"
    @GetMapping("/")
    public String index(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "index";
    }

    // Оновлений метод для деталей
    @GetMapping("/item/{id}")
    public String itemDetail(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("item", item);
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