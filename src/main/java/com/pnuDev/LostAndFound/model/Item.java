package com.pnuDev.LostAndFound.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String contactPhone;

    @Column(nullable = false)
    private String personName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemType type;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Item() {}

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }
    public String getContactPhone() { return contactPhone; }
    public String getPersonName() { return personName; }
    public ItemType getType() { return type; }
    public String getImageUrl() { return imageUrl; }
    public User getUser() { return user; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(Category category) { this.category = category; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public void setPersonName(String personName) { this.personName = personName; }
    public void setType(ItemType type) { this.type = type; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setUser(User user) { this.user = user; }
}
