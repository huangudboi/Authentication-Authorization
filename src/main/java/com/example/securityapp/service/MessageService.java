package com.example.securityapp.service;

import com.example.securityapp.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> findAll();
    Message sendMessage(Message message);
    void deleteMessage(Long id);
    Message findById(Long id);
    List<Message> findByUserName(String userName);

}
