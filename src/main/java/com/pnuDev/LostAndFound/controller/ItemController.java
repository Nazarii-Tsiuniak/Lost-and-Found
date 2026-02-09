package com.pnuDev.LostAndFound.controller;

import com.pnuDev.LostAndFound.model.Category;
import com.pnuDev.LostAndFound.model.Item;
import com.pnuDev.LostAndFound.model.ItemType;
import com.pnuDev.LostAndFound.model.User;
import com.pnuDev.LostAndFound.repository.ItemRepository;
import com.pnuDev.LostAndFound.repository.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;

@Controller
@RequestMapping("/report")
public class ItemController {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemController(ItemRepository itemRepository,
                          UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/lost")
    public String saveLostItem(
            @RequestParam String title,
            @RequestParam String ownerName,
            @RequestParam String category,
            @RequestParam String location,
            @RequestParam String date,
            @RequestParam String phone,
            @RequestParam String description,
            Authentication authentication
    ) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        Item item = new Item();
        item.setTitle(title);
        item.setPersonName(ownerName);
        item.setCategory(Category.valueOf(category.toUpperCase()));
        item.setLocation(location);
        item.setDate(LocalDate.parse(date));
        item.setContactPhone(phone);
        item.setDescription(description);
        item.setType(ItemType.LOST); // ✅ використання enum
        item.setUser(user);

        itemRepository.save(item);
        return "redirect:/my-items";
    }

    @PostMapping("/found")
    public String saveFoundItem(
            @RequestParam String title,
            @RequestParam String finderName,
            @RequestParam String category,
            @RequestParam String location,
            @RequestParam String date,
            @RequestParam String phone,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile image,
            Authentication authentication
    ) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow();

        Item item = new Item();
        item.setTitle(title);
        item.setPersonName(finderName);
        item.setCategory(Category.valueOf(category.toUpperCase()));
        item.setLocation(location);
        item.setDate(LocalDate.parse(date));
        item.setContactPhone(phone);
        item.setDescription(description);
        item.setType(ItemType.FOUND); // ✅ enum
        item.setUser(user);

        itemRepository.save(item);
        return "redirect:/my-items";
    }

    @PostMapping("/update")
    public String updateItem(
            @RequestParam Long id,
            @RequestParam String title,
            @RequestParam String personName,
            @RequestParam String category,
            @RequestParam String location,
            @RequestParam String date,
            @RequestParam String phone,
            @RequestParam String description,
            @RequestParam(required = false) MultipartFile image,
            Authentication authentication
    ) {
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow();
        Item item = itemRepository.findById(id).orElseThrow();

        if (!item.getUser().getId().equals(user.getId())) {
            return "redirect:/my-items";
        }

        item.setTitle(title);
        item.setPersonName(personName);
        item.setCategory(Category.valueOf(category));
        item.setLocation(location);
        item.setDate(LocalDate.parse(date));
        item.setContactPhone(phone);
        item.setDescription(description);

        //Later here should be added logic of working with image

        itemRepository.save(item);
        return "redirect:/my-items";
    }
}
