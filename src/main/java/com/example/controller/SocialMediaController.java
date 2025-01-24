package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
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
        if(accountService.accountNameTaken(account.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
        }
        accountService.register(account);
        return ResponseEntity.status(HttpStatus.OK).body("Sucessfully registered");
    }
    // @PostMapping("login")
    // public ResponseEntity<Void> login(@RequestBody Account account){

    // }

}
