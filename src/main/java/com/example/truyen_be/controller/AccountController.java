package com.example.truyen_be.controller;

import com.example.truyen_be.model.Account;
import com.example.truyen_be.repository.IAccountRepository;
import com.example.truyen_be.service.imp.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@CrossOrigin("*")

public class AccountController {
    @Autowired
    private IAccountRepository iAccountRepository;
    @Autowired
    private AccountService accountService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Account account) {
        boolean success = accountService.login(account.getName(), account.getPassword());
        if (success) {
            return ResponseEntity.ok("OK");
        } else {
            return ResponseEntity.status(401).body("Thông tin đăng nhập không chính xác");
        }
    }
}
