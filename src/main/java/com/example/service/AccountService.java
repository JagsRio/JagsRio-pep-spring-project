package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Account> registerAccount(Account newAccount){
        String username = newAccount.getUsername();
        String password = newAccount.getPassword();
        Account finalAccount = null;
        if (username.isEmpty() || password.length()<4){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else{
            finalAccount =  accountRepository.save(newAccount);
        }
        return new ResponseEntity<>(finalAccount, HttpStatus.OK);
    }

    public ResponseEntity<Account> loginAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(username, password);
        if (optionalAccount.isPresent()){
            return new ResponseEntity<>(optionalAccount.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
