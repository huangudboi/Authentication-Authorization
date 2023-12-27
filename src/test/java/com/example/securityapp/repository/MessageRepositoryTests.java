package com.example.securityapp.repository;

import com.example.securityapp.model.Message;
import com.example.securityapp.model.Order;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MessageRepositoryTests {

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void MessageRepository_SendMessage_ReturnsSavedMessage() {
        Message message = Message.builder().userName("user2001").password("0981729501").messages("Hello").created_on("27-12-2023")
                .build();

        Message savedMessage = messageRepository.save(message);

        Assertions.assertThat(savedMessage).isNotNull();
        Assertions.assertThat(savedMessage.getId()).isGreaterThan(0);
    }

    @Test
    public void MessageRepository_GetAllMessage_ReturnsMoreThanOneMessage() {
        Message message = Message.builder().userName("user2001").password("0981729501").messages("Hello").created_on("27-12-2023")
                .build();
        Message message2 = Message.builder().userName("user2001").password("0981729501").messages("Good morning").created_on("27-12-2023")
                .build();

        messageRepository.save(message);
        messageRepository.save(message2);

        List<Message> messageList = messageRepository.findAll();

        Assertions.assertThat(messageList).isNotNull();
        Assertions.assertThat(messageList.size()).isEqualTo(2);
    }

    @Test
    public void MessageRepository_FindByMessageId_ReturnsSavedMessage() {
        Message message = Message.builder().userName("user2001").password("0981729501").messages("Hello").created_on("27-12-2023")
                .build();

        messageRepository.save(message);

        Message savedMessage = messageRepository.findById(message.getId()).get();

        Assertions.assertThat(savedMessage).isNotNull();
    }

    @Test
    public void MessageRepository_DeleteMessageById_ReturnVoid() {
        Message message = Message.builder().userName("user2001").password("0981729501").messages("Hello").created_on("27-12-2023")
                .build();

        messageRepository.save(message);

        messageRepository.deleteById(message.getId());
        Optional<Message> messageReturn = messageRepository.findById(message.getId());

        Assertions.assertThat(messageReturn).isEmpty();
    }

    @Test
    public void MessageRepository_FindAllByUserName_ReturnsMoreThanOneMessage() {
        Message message = Message.builder().userName("user2001").password("0981729501").messages("Hello").created_on("27-12-2023")
                .build();
        Message message2 = Message.builder().userName("user2001").password("0981729501").messages("Good morning").created_on("27-12-2023")
                .build();

        messageRepository.save(message);
        messageRepository.save(message2);

        List<Message> messageList = messageRepository.findByUserName("user2001");

        Assertions.assertThat(messageList).isNotNull();
        Assertions.assertThat(messageList.size()).isEqualTo(2);
    }
}
