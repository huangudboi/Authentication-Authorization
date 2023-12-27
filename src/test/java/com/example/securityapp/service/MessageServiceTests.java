package com.example.securityapp.service;


import com.example.securityapp.model.Message;
import com.example.securityapp.repository.MessageRepository;
import com.example.securityapp.service.impl.MessageServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTests {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Message message;

    @BeforeEach
    public void init() {
        message = Message.builder().userName("user2001").password("0981729501").messages("Hello").created_on("27-12-2023")
                .build();
    }

    @Test
    public void MessageService_FindAllMessage() {

        List<Message> messageList = Mockito.mock(List.class);

        when(messageRepository.findAll()).thenReturn(messageList);

        List<Message> saveMessage = messageService.findAll();

        Assertions.assertThat(saveMessage).isNotNull();
    }

    @Test
    public void MessageService_SendMessage_ReturnMessage() {
        when(messageRepository.save(Mockito.any(Message.class))).thenReturn(message);

        Message savedMessage = messageService.sendMessage(message);

        Assertions.assertThat(savedMessage).isNotNull();
    }

    @Test
    public void MessageService_DeleteMessageById_ReturnVoid() {
        long messageId = 1L;

        when(messageRepository.findById(messageId)).thenReturn(Optional.ofNullable(message));

        doNothing().when(messageRepository).delete(message);

        assertAll(() -> messageService.deleteMessage(messageId));
    }

    @Test
    public void MessageService_FindMessageById_ReturnMessage() {
        long messageId = 1L;

        when(messageRepository.findById(messageId)).thenReturn(Optional.ofNullable(message));

        Message messageReturn = messageService.findById(messageId);

        Assertions.assertThat(messageReturn).isNotNull();
    }

    @Test
    public void MessageService_FindByUserName() {

        List<Message> messageList = Mockito.mock(List.class);

        when(messageRepository.findByUserName("user2001")).thenReturn(messageList);

        List<Message> saveMessage = messageService.findByUserName("user2001");

        Assertions.assertThat(saveMessage).isNotNull();
    }
}
