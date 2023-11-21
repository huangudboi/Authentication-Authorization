package com.example.securityapp.service;

import com.example.securityapp.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    List<Message> findAll();
    void save(Message message);
    void remove(Message message);
    Optional<Message> findById(Long id);
    List<Message> findByUserName(String userName);

}
