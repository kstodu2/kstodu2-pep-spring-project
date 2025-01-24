package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account) {
        
        return accountRepository.save(account);

    }

    public boolean accountNameTaken(String accountName){
        List<Account> allAccounts = accountRepository.findAll();
        
        for(Account all: allAccounts){
            if(all.getUsername().equals(accountName)){
                return true;
            }
        }
        return false;
    }

}
