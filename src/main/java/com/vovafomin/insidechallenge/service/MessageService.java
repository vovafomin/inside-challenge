package com.vovafomin.insidechallenge.service;

import com.vovafomin.insidechallenge.dto.MessageDTO;
import com.vovafomin.insidechallenge.entity.Message;
import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.mapper.MessageMapper;
import com.vovafomin.insidechallenge.repository.MessageRepository;
import com.vovafomin.insidechallenge.request.MessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
  private final MessageRepository messageRepository;
  private final MessageMapper messageMapper;

  @Autowired
  public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
    this.messageRepository = messageRepository;
    this.messageMapper = messageMapper;
  }

  public Optional<List<MessageDTO>> process(MessageRequest messageRequest, User user) {
    if (messageRequest.getMessage().startsWith("history")) {
      String[] split = messageRequest.getMessage().split(" ");
      List<Message> entities;
      if (split.length > 1) {
        int limit = Integer.parseInt(split[1]);
        if (limit > 0) {
          entities = messageRepository.findAllByUser(user, PageRequest.of(0, limit));
        } else {
          entities = Collections.emptyList();
        }
      } else {
        entities = messageRepository.findAllByUser(user);
      }
      return Optional.of(
          entities.stream().map(messageMapper::mapMessage).collect(Collectors.toList()));
    } else {
      create(messageRequest.getMessage(), user);
      return Optional.empty();
    }
  }

  public Message create(String value, User user) {
    Message message = new Message();
    message.setMessage(value);
    message.setUser(user);
    return messageRepository.save(message);
  }
}
