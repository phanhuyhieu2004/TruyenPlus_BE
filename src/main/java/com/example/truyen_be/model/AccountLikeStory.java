package com.example.truyen_be.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "account_like_story")
public class AccountLikeStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @Column(name = "liked_at")
    private LocalDateTime likedAt;

    public AccountLikeStory() {}

    public AccountLikeStory(Account account, Story story) {
        this.account = account;
        this.story = story;
        this.likedAt = LocalDateTime.now();
    }
}

