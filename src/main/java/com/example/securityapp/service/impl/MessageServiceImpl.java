package com.example.securityapp.service.impl;

import com.example.securityapp.exceptions.OrderNotFoundException;
import com.example.securityapp.model.Message;
import com.example.securityapp.model.Order;
import com.example.securityapp.repository.MessageRepository;
import com.example.securityapp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void deleteMessage(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Message could not be found"));
        messageRepository.delete(message);
    }

    @Override
    public Message findById(Long id) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Message could not be found"));
        return message;
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findByUserName(String userName) {
        return messageRepository.findByUserName(userName);
    }
}