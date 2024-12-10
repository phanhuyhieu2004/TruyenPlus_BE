package com.example.truyen_be.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private String name;
    private String password;

    public AccountDTO() {
    }

    public AccountDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }
}

