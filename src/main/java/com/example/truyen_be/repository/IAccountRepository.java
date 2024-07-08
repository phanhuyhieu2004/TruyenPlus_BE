package com.example.truyen_be.repository;

import com.example.truyen_be.model.Account;
import com.example.truyen_be.service.IGeneralService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account,Long> {
    public Account findByName(String name);
}
