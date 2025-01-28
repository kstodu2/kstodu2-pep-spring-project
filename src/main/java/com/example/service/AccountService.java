package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account account){
        return accountRepository.save(account);

    }
    
    public Optional<Account> login(String username, String password){
        return accountRepository.findByUsernameAndPassword(username, password);
    }

    public boolean accountExists(Account account){
        if(accountRepository.findByUsername(account.getUsername()).isEmpty()){
            return false;
        }
        return true;
    }
    public boolean accountExistsById(Integer id){
        if(accountRepository.findById(id).isPresent()){
            return true;
        }
        return false;
    }
  

}
