package com.example.securityapp.controller;


import com.example.securityapp.model.Message;
import com.example.securityapp.model.Order;
import com.example.securityapp.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MessageControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    private Message message;
    private Message message2;

    @BeforeEach
    public void init() {
        message = Message.builder().userName("user2001").password("0981729501").messages("Hello").created_on("27-12-2023")
                .build();
        message2 = Message.builder().userName("user2001").password("0981729501").messages("Good morning").created_on("27-12-2023")
                .build();
    }

    @Test
    public void MessageController_SendMessage_ReturnCreated() throws Exception {
        given(messageService.sendMessage(ArgumentMatchers.any())).willAnswer((invocation -> invocation.getArgument(0)));

        ResultActions response = mockMvc.perform(post("/api/v1/message/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", CoreMatchers.is(message.getUserName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(message.getPassword())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages", CoreMatchers.is(message.getMessages())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_on", CoreMatchers.is(message.getCreated_on())));
    }

    @Test
    public void MessageController_GetAllMessage_ReturnListMessage() throws Exception {
        List<Message> messageList = new ArrayList<Message>();

        messageList.add(message);
        messageList.add(message2);

        when(messageService.findAll()).thenReturn(messageList);

        ResultActions response = mockMvc.perform(get("/api/v1/message/getAll")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(messageList.size())));
    }

    @Test
    public void MessageController_MessageDetail_ReturnMessage() throws Exception {
        long messageId = 1L;
        when(messageService.findById(messageId)).thenReturn(message);

        ResultActions response = mockMvc.perform(get("/api/v1/message/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(message)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", CoreMatchers.is(message.getUserName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(message.getPassword())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.messages", CoreMatchers.is(message.getMessages())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.created_on", CoreMatchers.is(message.getCreated_on())));
    }

    @Test
    public void MessageController_DeleteMessage_ReturnString() throws Exception {
        long messageId = 1L;
        doNothing().when(messageService).deleteMessage(messageId);

        ResultActions response = mockMvc.perform(delete("/api/v1/message/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void MessageController_GetMessageByUsername_ReturnListMessage() throws Exception {
        List<Message> messageList = new ArrayList<Message>();

        messageList.add(message);
        messageList.add(message2);

        when(messageService.findByUserName(message.getUserName())).thenReturn(messageList);

        ResultActions response = mockMvc.perform(get("/api/v1/message")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userName","user2001"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(messageList.size())));
    }

}
