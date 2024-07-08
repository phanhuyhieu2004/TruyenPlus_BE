package com.example.truyen_be.service.imp;

import com.example.truyen_be.model.Account;
import com.example.truyen_be.repository.IAccountRepository;
import com.example.truyen_be.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return Optional.empty();
    }

    @Override
    public Account save(Account account) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
    public boolean login(String name, String password) {
        Account account = iAccountRepository.findByName(name);
        return account != null && account.getPassword().equals(password);
    }
}
