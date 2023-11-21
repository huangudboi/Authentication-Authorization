package com.example.securityapp.controller;

import com.example.securityapp.model.Message;
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
    public ResponseEntity<Message> createMessage(@RequestBody Message message, UriComponentsBuilder builder) {
        messageService.save(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.path("/{id}").buildAndExpand(message.getId()).toUri());
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable("id") Long id) {
        Optional<Message> message = messageService.findById(id);
        if (!message.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        messageService.remove(message.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> getMessageById(@PathVariable("id") Long id) {
        Optional<Message> message = messageService.findById(id);

        if (!message.isPresent()) {
            return new ResponseEntity<>(message.get(), HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(message.get(), HttpStatus.OK);
    }

    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Message>> getMessageByUsername(@RequestParam(value="username") String username) {
        List<Message> messages = messageService.findByUserName(username);
        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
