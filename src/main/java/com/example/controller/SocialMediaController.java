package com.example.controller;

import java.util.List;
import java.util.Optional;

import javax.naming.AuthenticationException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

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
    public ResponseEntity<String> register(@RequestBody Account account){

        if(account.getUsername().isBlank() || (account.getPassword()).length() < 3){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }
        if(accountService.accountExists(account)){
             return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
        }
        accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK).body("Sucessfully registered");
        
    }
    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody Account account){
        Optional accountLog = accountService.login(account.getUsername(), account.getPassword());
        if(accountLog.isPresent()){
            return new ResponseEntity<>(accountLog, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials");

    }
    @PostMapping("messages")
    public ResponseEntity<Object> messages(@RequestBody Message message){
        if(message.getMessageText().isBlank() 
        || message.getMessageText().length() > 255
        || !accountService.accountExistsById(message.getPostedBy())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message invalid.");
        }
        messageService.createNewMessage(message);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    @GetMapping("messages")
    public ResponseEntity<List> messages(){
        List<Message> list = messageService.getAllMessages();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id){
        Optional<Message> messageSearch = messageService.getMessageByMessageId(message_id);
        return new ResponseEntity<>(messageSearch.orElse(null), HttpStatus.OK);
    }
    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer message_id){
        if(!messageService.deleteMessageById(message_id)){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>("1", HttpStatus.OK);
    }
    @PatchMapping("messages/{message_id}")
    public ResponseEntity<String> updateMessage(@PathVariable Integer message_id, @RequestBody Message message){

        if(!messageService.getMessageByMessageId(message_id).isPresent() 
            || message.getMessageText().isEmpty() 
            || message.getMessageText().length() > 255){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            messageService.deleteMessageById(message_id);
            return ResponseEntity.status(HttpStatus.OK).body("1");
    }

    @GetMapping("accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFromAccountId(@PathVariable Integer account_id){

        List<Message> listOfMessages = messageService.getAllMessagesFromAccountId(account_id);

        return ResponseEntity.status(HttpStatus.OK).body(listOfMessages);
        
    }

}
