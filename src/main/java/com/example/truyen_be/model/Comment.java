package com.example.truyen_be.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;
    @Column(name = "created_at", nullable = false, updatable = false)

    private LocalDateTime createdAt;

    public Comment() {
    }

    public Comment(Long id, String content, Account account, Chapter chapter, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.account = account;
        this.chapter = chapter;
        this.createdAt = createdAt;
    }
}
