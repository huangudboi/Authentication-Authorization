package com.example.securityapp.controller;

import com.example.securityapp.model.Message;
import com.example.securityapp.model.Order;
import com.example.securityapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Message>> findALlMessage() {
        List<Message> messages = messageService.findAll();
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping(value = "/chat")
    public ResponseEntity<Message> sendMessage(@RequestBody Message message, UriComponentsBuilder builder) {
        messageService.sendMessage(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(message.getId()).toUri());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable("id") Long id) {
        ResponseEntity<String> response;
        try {
            messageService.deleteMessage(id);
            response = new ResponseEntity<>("Delete message successful", HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessageById(@PathVariable("id") Long id) {
        ResponseEntity<Message> response;
        try {
            Message message = messageService.findById(id);
            response = new ResponseEntity<>(message, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Message>> getMessageByUsername(@RequestParam(value="userName") String userName) {
        List<Message> messages = messageService.findByUserName(userName);
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
