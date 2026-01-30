package com.pnuDev.LostAndFound.controller;

import com.pnuDev.LostAndFound.model.Category;
import com.pnuDev.LostAndFound.model.Item;
import com.pnuDev.LostAndFound.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;

@Controller
@RequestMapping("/report")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @PostMapping("/lost")
    public String saveLostItem(@RequestParam String title,
                               @RequestParam String ownerName,
                               @RequestParam String category,
                               @RequestParam String location,
                               @RequestParam String date,
                               @RequestParam String phone,
                               @RequestParam String description,
                               @RequestParam(required = false) MultipartFile image) {

        Item item = new Item();
        item.setTitle(title);
        item.setPersonName(ownerName);
        item.setCategory(Category.valueOf(category));
        item.setLocation(location);
        item.setDate(LocalDate.parse(date));
        item.setContactPhone(phone);
        item.setDescription(description);
        item.setType("LOST");

        itemRepository.save(item);
        return "redirect:/?success";
    }

    @PostMapping("/found")
    public String saveFoundItem(@RequestParam String title,
                                @RequestParam String finderName,
                                @RequestParam String category,
                                @RequestParam String location,
                                @RequestParam String date,
                                @RequestParam String phone,
                                @RequestParam String description,
                                @RequestParam MultipartFile image) {

        Item item = new Item();
        item.setTitle(title);
        item.setPersonName(finderName);
        item.setCategory(Category.valueOf(category));
        item.setLocation(location);
        item.setDate(LocalDate.parse(date));
        item.setContactPhone(phone);
        item.setDescription(description);
        item.setType("FOUND");

        itemRepository.save(item);
        return "redirect:/?success";
    }
}