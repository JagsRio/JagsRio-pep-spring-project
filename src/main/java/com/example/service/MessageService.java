package com.example.service;

import org.h2.util.json.JSONObject;
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

    public ResponseEntity<List<Message>> getAllMessages(){
        List<Message> messageList = new ArrayList<>();
        messageList = messageRepository.findAll();
        return new ResponseEntity<>(messageList, HttpStatus.OK);
    }

    public ResponseEntity<Message> getByMessageId(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()){
            return new ResponseEntity<>(optionalMessage.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    public ResponseEntity<String> deleteMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()){
            messageRepository.deleteById(messageId);
            return new ResponseEntity<>("1", HttpStatus.OK);
        }
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    public ResponseEntity<String> updateMessageById(Integer messageId, String messageText){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (messageText.isEmpty() || messageText.length()>255 || messageText.length()==0){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (!optionalMessage.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Message msg = optionalMessage.get();
        msg.setMessageText(messageText);
        messageRepository.save(msg);
        return new ResponseEntity<>("1", HttpStatus.OK);
    }

    public ResponseEntity<List<Message>> getMessagesByAccountId(Integer accountId){
        List<Message> messagesByAccount = new ArrayList<>();
        messagesByAccount = messageRepository.findAllByPostedBy(accountId);
        return new ResponseEntity<>(messagesByAccount, HttpStatus.OK);
    }
}
