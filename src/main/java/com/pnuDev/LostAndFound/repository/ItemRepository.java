package com.pnuDev.LostAndFound.repository;

import com.pnuDev.LostAndFound.model.Category;
import com.pnuDev.LostAndFound.model.Item;
import com.pnuDev.LostAndFound.model.ItemType;
import com.pnuDev.LostAndFound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(ItemType type);
    List<Item> findByCategory(Category category);
    List<Item> findByTypeAndCategory(ItemType type, Category category);
    List<Item> findByUser(User user);
}