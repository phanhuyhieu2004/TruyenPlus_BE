package com.example.truyen_be.controller;

import com.example.truyen_be.dto.AccountDTO;
import com.example.truyen_be.model.Account;
import com.example.truyen_be.model.ErrorResponse;
import com.example.truyen_be.repository.IAccountRepository;
import com.example.truyen_be.service.imp.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> login(@RequestBody AccountDTO accountDTO) {
        try {
            Account account = accountService.login(accountDTO.getName(), accountDTO.getPassword());
            return new ResponseEntity<>(account, HttpStatus.OK);
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Không có tài khoản")) {
                return new ResponseEntity<>(new ErrorResponse("Không có tài khoản"),HttpStatus.BAD_REQUEST);
            }  if (errorMessage.contains("Sai mat khau")) {
                return new ResponseEntity<>(new ErrorResponse("Sai mật khẩu"), HttpStatus.BAD_REQUEST);
            }else {
                return new ResponseEntity<>(new ErrorResponse("Lỗi không xác đinh"),HttpStatus.BAD_REQUEST);

            }

        }}
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountDTO accountDTO) {
        try {
            Account account = accountService.register(accountDTO.getName(), accountDTO.getPassword());
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Tài khoản đã tồn tại")) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Tài khoản đã tồn taị"));
            } else {
                return ResponseEntity.badRequest().body(new ErrorResponse("Lỗi không xác định"));

            }
        }
    }

}
