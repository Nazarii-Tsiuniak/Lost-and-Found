package com.pnuDev.LostAndFound;

import com.pnuDev.LostAndFound.model.Item;
import com.pnuDev.LostAndFound.model.User;
import com.pnuDev.LostAndFound.repository.ItemRepository;
import com.pnuDev.LostAndFound.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.security.core.Authentication;

import java.util.List;

@Controller
public class WebController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository; // ✅ додано

    public WebController(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "index";
    }

    @GetMapping("/item/{id}")
    public String itemDetail(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));
        model.addAttribute("item", item);
        return "item-detail";
    }

    @GetMapping("/login")
    public String login() {
        return "auth";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/my-items")
    public String myItems(Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        model.addAttribute("items", itemRepository.findByUser(user));
        return "my-items";
    }

    @GetMapping("/report/found")
    public String reportFound() {
        return "report-found";
    }

    @GetMapping("/report/lost")
    public String reportLost() {
        return "report-lost";
    }
}
