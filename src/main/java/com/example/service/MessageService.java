package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import com.example.repository.*;
import java.util.*;

@Service
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository=accountRepository;
    }

    public ResponseEntity<Message> createMessage(Message newMessage){
        String messageText = newMessage.getMessageText();
        Integer postedBy = newMessage.getPostedBy();

        Message finalMessage=null;


        Optional<Account> optionalAccount = accountRepository.findById(postedBy);
        if (optionalAccount.isPresent()){
            if (messageText.length()>255 || messageText.isEmpty()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);    
            }
            else{
            finalMessage = messageRepository.save(newMessage);
            }
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(finalMessage,HttpStatus.OK);

    }

}
