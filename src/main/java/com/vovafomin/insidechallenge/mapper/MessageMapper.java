package com.vovafomin.insidechallenge.mapper;

import com.vovafomin.insidechallenge.dto.MessageDTO;
import com.vovafomin.insidechallenge.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {

  private final UserMapper userMapper;

  @Autowired
  public MessageMapper(UserMapper userMapper) {
    this.userMapper = userMapper;
  }

  public MessageDTO mapMessage(Message message) {
    return MessageDTO.builder()
        .id(message.getId())
        .message(message.getMessage())
        .user(userMapper.mapUser(message.getUser()))
        .build();
  }
}
