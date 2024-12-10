package com.example.truyen_be.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String name;
    private String password;
    private int role;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Account() {
    }

    public Account(Long accountId, String name, String password, int role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.accountId = accountId;
        this.name = name;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
