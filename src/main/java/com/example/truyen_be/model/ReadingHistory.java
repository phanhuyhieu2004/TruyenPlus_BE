package com.example.truyen_be.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "reading_history")
public class ReadingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(name = "read_at", nullable = false)
    private LocalDateTime readAt;

    public ReadingHistory() {
    }

    public ReadingHistory(Account account, Story story, Chapter chapter, LocalDateTime readAt) {
        this.account = account;
        this.story = story;
        this.chapter = chapter;
        this.readAt = readAt;
    }
}
