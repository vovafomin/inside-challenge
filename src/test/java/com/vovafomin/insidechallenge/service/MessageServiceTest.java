package com.vovafomin.insidechallenge.service;

import com.vovafomin.insidechallenge.entity.Message;
import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.mapper.MessageMapper;
import com.vovafomin.insidechallenge.mapper.UserMapper;
import com.vovafomin.insidechallenge.repository.MessageRepository;
import com.vovafomin.insidechallenge.request.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MessageServiceTest {

  @Mock private MessageRepository messageRepository;
  private final UserMapper userMapper = new UserMapper();
  private final MessageMapper messageMapper = new MessageMapper(userMapper);
  private MessageService messageService;
  private final User user =
      User.builder()
          .id(UUID.randomUUID().toString())
          .username("test_user")
          .password("test_password")
          .build();

  @BeforeEach
  public void beforeEach() {
    messageService = new MessageService(messageRepository, messageMapper);
    when(messageRepository.save(any(Message.class))).thenAnswer(a -> a.getArgument(0));
    when(messageRepository.findAllByUser(any(User.class))).thenReturn(Collections.emptyList());
    when(messageRepository.findAllByUser(any(User.class), any(Pageable.class)))
        .thenReturn(Collections.emptyList());
  }

  @Test
  public void createReturnsValidMessage() {
    final String testMessage = "Test message";
    Message message = messageService.create(testMessage, user);
    assertThat(message.getMessage()).isEqualTo(testMessage);
    assertThat(message.getUser()).usingRecursiveComparison().isEqualTo(user);
  }

  @Test
  public void processReturnsEmptyOptionalIfHistoryKeywordIsNotPresent() {
    var result =
        messageService.process(MessageRequest.builder().message("Test message").build(), user);
    assertThat(result).isEmpty();
  }

  @Test
  public void processReturnsCollectionOfMessagesIfHistoryKeywordIsPresent() {
    var result =
        messageService.process(MessageRequest.builder().message("history 10").build(), user);
    assertThat(result).isNotEmpty();
  }
}
