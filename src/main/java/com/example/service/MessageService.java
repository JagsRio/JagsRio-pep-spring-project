package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.*;
import com.example.exception.BadRequestException;
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

    public Message createMessage(Message newMessage){
        String messageText = newMessage.getMessageText();
        Integer postedBy = newMessage.getPostedBy();
        Message finalMessage=null;

        Optional<Account> optionalAccount = accountRepository.findById(postedBy);
        if (optionalAccount.isPresent()){
            if (messageText.length()>255 || messageText.isEmpty()){
                throw new BadRequestException("Invalid message text. Text length should be 1 to 255.");
            }
            else{
                finalMessage = messageRepository.save(newMessage);
            }
        }
        else{
            throw new BadRequestException("Account not found. Create or Login to post message");
        }
        return finalMessage;
    }

    public List<Message> getAllMessages(){
        List<Message> messageList = new ArrayList<>();
        messageList = messageRepository.findAll();
        return messageList;
    }

    public Message getByMessageId(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()){
            return optionalMessage.get();
        }
        return null;
    }
    
    public String deleteMessageById(Integer messageId){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()){
            messageRepository.deleteById(messageId);
            return "1";
        }
        return "";
    }

    public String updateMessageById(Integer messageId, String messageText){
        Optional<Message> optionalMessage = messageRepository.findById(messageId);

        if (messageText.isEmpty() || messageText.length()>255 || messageText.length()==0){
            throw new BadRequestException("Invalid message text. Text length should be 1 to 255.");
        }
        if (!optionalMessage.isPresent()){
            throw new BadRequestException("Message not found by MessageId:" + messageId + " for update");
        }
        Message msg = optionalMessage.get();
        msg.setMessageText(messageText);
        messageRepository.save(msg);
        return "1";
    }

    public List<Message> getMessagesByAccountId(Integer accountId){
        List<Message> messagesByAccount = new ArrayList<>();
        messagesByAccount = messageRepository.findAllByPostedBy(accountId);
        return messagesByAccount;
    }
}
