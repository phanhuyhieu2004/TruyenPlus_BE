package com.example.truyen_be.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    private String name;
    private String password;

    public Account() {
    }

    public Account(Long accountId, String name, String password) {
        this.accountId = accountId;
        this.name = name;
        this.password = password;
    }
}
