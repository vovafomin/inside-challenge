package com.vovafomin.insidechallenge.service;

import com.vovafomin.insidechallenge.entity.Message;
import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.mapper.MessageMapper;
import com.vovafomin.insidechallenge.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MessageMapperTest {

  @Test
  public void mapMessageReturnsValidDTO() {
    User user =
        User.builder()
            .id(UUID.randomUUID().toString())
            .username("test_user")
            .password("test_password")
            .build();
    Message message =
        Message.builder()
            .id(UUID.randomUUID().toString())
            .message("Test message")
            .user(user)
            .build();
    var dto = new MessageMapper(new UserMapper()).mapMessage(message);
    assertThat(dto).usingRecursiveComparison().isEqualTo(message);
  }
}
