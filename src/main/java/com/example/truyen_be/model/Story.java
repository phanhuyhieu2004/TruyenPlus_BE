package com.example.truyen_be.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "storys")
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    private Long storyId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;
    @Column(columnDefinition = "longtext")
    private String image;


    @Column(columnDefinition = "longtext")
    private String description;

    @Column(name = "author", length = 255)
    private String author;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Column(name = "status")
    private String status;

    @ManyToMany
    @JoinTable(
            name = "story_category",
            joinColumns = @JoinColumn(name = "story_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();



    private int totalChapters;

    private int view;
    private int likes;

    public Story() {

    }

    public Story(Long storyId, String title, String image, String description, String author, LocalDateTime createdAt, LocalDateTime updatedAt, String status, Set<Category> categories, int totalChapters, int view, int likes) {
        this.storyId = storyId;
        this.title = title;
        this.image = image;
        this.description = description;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.categories = categories;
        this.totalChapters = totalChapters;
        this.view = view;
        this.likes = likes;
    }
}