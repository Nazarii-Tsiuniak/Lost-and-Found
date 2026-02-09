package com.pnuDev.LostAndFound;

import com.pnuDev.LostAndFound.model.Category;
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

import com.pnuDev.LostAndFound.model.ItemType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pnuDev.LostAndFound.service.ImageService;

@Controller
public class WebController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public WebController(ItemRepository itemRepository, UserRepository userRepository, ImageService imageService) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String index(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "category", required = false) String category,
            Model model
    ) {
        List<Item> items;

        ItemType typeEnum = null;
        Category categoryEnum = null;

        if (type != null && !type.isEmpty()) {
            try { typeEnum = ItemType.valueOf(type.toUpperCase()); } catch (Exception e) {}
        }
        if (category != null && !category.isEmpty()) {
            try { categoryEnum = Category.valueOf(category.toUpperCase()); } catch (Exception e) {}
        }

        if (typeEnum != null && categoryEnum != null) {
            items = itemRepository.findByTypeAndCategory(typeEnum, categoryEnum);
        } else if (typeEnum != null) {
            items = itemRepository.findByType(typeEnum);
        } else if (categoryEnum != null) {
            items = itemRepository.findByCategory(categoryEnum);
        } else {
            items = itemRepository.findAll();
        }

        model.addAttribute("items", items);
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", Category.values());

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
    public String profile(Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        List<Item> lostItems = itemRepository.findByUserAndType(user, ItemType.LOST);
        List<Item> foundItems = itemRepository.findByUserAndType(user, ItemType.FOUND);

        model.addAttribute("user", user);
        model.addAttribute("lostItems", lostItems);
        model.addAttribute("foundItems", foundItems);

        return "profile";
    }

    @GetMapping("/my-items")
    public String myItems(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "category", required = false) String category,
            Model model,
            Authentication authentication
    ) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        List<Item> items;

        ItemType typeEnum = null;
        Category categoryEnum = null;

        if (type != null && !type.isEmpty()) {
            try { typeEnum = ItemType.valueOf(type.toUpperCase()); } catch (Exception e) {}
        }
        if (category != null && !category.isEmpty()) {
            try { categoryEnum = Category.valueOf(category.toUpperCase()); } catch (Exception e) {}
        }

        if (typeEnum != null && categoryEnum != null) {
            items = itemRepository.findByUserAndTypeAndCategory(user, typeEnum, categoryEnum);
        } else if (typeEnum != null) {
            items = itemRepository.findByUserAndType(user, typeEnum);
        } else if (categoryEnum != null) {
            items = itemRepository.findByUserAndCategory(user, categoryEnum);
        } else {
            items = itemRepository.findByUser(user);
        }

        model.addAttribute("items", items);
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("categories", Category.values());

        return "my-items";
    }


    @PostMapping("/item/{id}/delete")
    public String deleteItem(@PathVariable Long id, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        if (item.getUser().getId().equals(user.getId())) {
            String imageUrl = item.getImageUrl();

            itemRepository.delete(item);

            if (imageUrl != null) {
                imageService.deleteImage(imageUrl);
            }
        }

        return "redirect:/my-items";
    }

    @GetMapping("/report/found")
    public String reportFound() {
        return "report-found";
    }

    @GetMapping("/report/lost")
    public String reportLost() {
        return "report-lost";
    }

    @GetMapping("/item/{id}/edit")
    public String editItemPage(@PathVariable Long id, Model model, Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item Id:" + id));

        if (!item.getUser().getId().equals(user.getId())) {
            return "redirect:/my-items";
        }

        model.addAttribute("item", item);
        model.addAttribute("categories", Category.values());
        return "edit-item";
    }
}
