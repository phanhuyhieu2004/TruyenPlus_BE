package com.example.truyen_be.service.imp;

import com.example.truyen_be.model.Account;
import com.example.truyen_be.repository.IAccountRepository;
import com.example.truyen_be.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Override
    public Iterable<Account> findAll() {
        return null;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return iAccountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
    public Account login(String name, String pass) {

        Account account = iAccountRepository.findByName(name);
        if (account == null) {
            throw new RuntimeException("Không có tài khoản");
        }
        if (!account.getPassword().equals(pass)) {
            throw new RuntimeException("Sai mat khau");
        }
        return account;
    }

    //    xử lý nghiệp vụ của chức năng đăng ký bằng cách tên ,mật khẩu
    public Account register(String name, String pass) {
        if (iAccountRepository.findByName(name) != null) {
            throw new RuntimeException("Tài khoản đã tồn tại");
        }

        Account accounts = new Account();
        accounts.setName(name);
        accounts.setPassword(pass);
        accounts.setCreatedAt(LocalDateTime.now());

        accounts.setRole(1);
        return iAccountRepository.save(accounts);
    }
}
