package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

@Controller
@RequestMapping("")
public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public @ResponseBody ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        Account registeredAccount = accountService.register(account);
        if (registeredAccount == null) {
            return ResponseEntity.status(409).body(null);
        }

        return ResponseEntity.ok().body(registeredAccount);
    }

    @PostMapping("login")
    public @ResponseBody ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        Account loggedInAccount = accountService.login(account);
        if (loggedInAccount == null) {
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.ok().body(loggedInAccount);
    }

    @PostMapping("messages")
    public @ResponseBody ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message addedMessage = messageService.addNewMessage(message);
        if (addedMessage == null) {
            return ResponseEntity.status(400).body(null);
        }

        return ResponseEntity.ok().body(addedMessage);
    }

    @RequestMapping(method = RequestMethod.GET, path = "messages")
    public @ResponseBody List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Message> findMessage(@PathVariable int messageId) {
        return new ResponseEntity<>(messageService.findMessage(messageId), HttpStatus.OK);
    }

    @DeleteMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        Integer result = messageService.deleteMessage(messageId);
        return ResponseEntity.ok().body(result != 1 ? null : result);
    }

    @PatchMapping("messages/{messageId}")
    public @ResponseBody ResponseEntity<Integer> updateMessage(@PathVariable int messageId, @RequestBody Message newMessage) {
        Message updatedMessage = messageService.updateMessage(newMessage);
        
        if (updatedMessage == null || messageService.findMessage(messageId) == null) {
            return ResponseEntity.status(400).body(null);
        }

        return ResponseEntity.ok().body(1);
    }

    @GetMapping("accounts/{accountId}/messages")
    public @ResponseBody List<Message> findMessagesByPostedBy(@PathVariable int accountId) {
        return messageService.getMessagesByPostedBy(accountId);
    }
}
