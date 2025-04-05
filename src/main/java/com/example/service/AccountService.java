package com.example.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.exception.BadRequestException;
import com.example.exception.ConflictRequestException;
import com.example.exception.UnAuthorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account newAccount){
        String username = newAccount.getUsername();
        String password = newAccount.getPassword();
        Account finalAccount = null;
        if (username.isEmpty() || password.length()<4){
            throw new BadRequestException("Invalid username/password. Check and try again.");
        }
        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()){
            throw new ConflictRequestException("Account already exists with the username:" + username);
        } else{
            finalAccount =  accountRepository.save(newAccount);
        }
        return finalAccount;
    }

    public Account loginAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();

        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(username, password);
        if (optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        throw new UnAuthorizedException("Invalid username/password. Try again");
    }
}
