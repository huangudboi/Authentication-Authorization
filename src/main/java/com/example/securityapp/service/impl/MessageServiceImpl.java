package com.example.securityapp.service.impl;

import com.example.securityapp.model.Message;
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
    public void save(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void remove(Message message) {
        messageRepository.delete(message);
    }

    @Override
    public Optional<Message> findById(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> findAll() {
        return (List<Message>) messageRepository.findAll();
    }

    @Override
    public List<Message> findByUserName(String userName) {
        return messageRepository.findByUserName(userName);
    }
}