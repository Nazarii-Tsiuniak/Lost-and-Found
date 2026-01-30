package com.pnuDev.LostAndFound.repository;

import com.pnuDev.LostAndFound.model.Item;
import com.pnuDev.LostAndFound.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByType(String type);
    List<Item> findByUser(User user);
}