package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createNewMessage(Message message){
        return messageRepository.save(message);
    }
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }
    public Optional<Message> getMessageByMessageId(Integer id){
        return messageRepository.findById(id);
    }
    public boolean deleteMessageById(Integer id){
        if(getMessageByMessageId(id).isPresent()){
            messageRepository.deleteById(id);
            return true;
        }
        return false;

    }
    public List<Message> getAllMessagesFromAccountId(Integer id){
        return messageRepository.findBypostedBy(id);
    }

}
