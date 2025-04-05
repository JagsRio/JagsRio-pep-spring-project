package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.*;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> registerUser(@RequestBody Account newAccount){
      return new ResponseEntity<>(accountService.registerAccount(newAccount), HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account){
        return new ResponseEntity<>(accountService.loginAccount(account), HttpStatus.OK);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message newMessage){
        return new ResponseEntity<>(messageService.createMessage(newMessage), HttpStatus.OK);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages(){
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        return new ResponseEntity<>(messageService.getByMessageId(messageId), HttpStatus.OK);
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<String> deleteByMessageId(@PathVariable Integer messageId){
        return new ResponseEntity<>(messageService.deleteMessageById(messageId), HttpStatus.OK);
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<String> updateMessage(@PathVariable Integer messageId, @RequestBody Message message){
        return new ResponseEntity<>(messageService.updateMessageById(messageId, message.getMessageText()), HttpStatus.OK);
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable Integer accountId){
        return new ResponseEntity<>(messageService.getMessagesByAccountId(accountId), HttpStatus.OK);
    }
}
