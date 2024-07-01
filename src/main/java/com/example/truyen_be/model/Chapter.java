package com.example.truyen_be.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "chapters")
public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long chapterId;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private  Story story;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "longtext")
    private String content;

    @Column(name = "chapter_number", nullable = false)
    private int chapterNumber;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;


    public Chapter() {

    }

    public Chapter(Long chapterId, Story story, String title, String content, Integer chapterNumber, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.chapterId = chapterId;
        this.story = story;
        this.title = title;
        this.content = content;
        this.chapterNumber = chapterNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}