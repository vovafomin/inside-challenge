package com.vovafomin.insidechallenge.controller;

import com.vovafomin.insidechallenge.dto.MessageDTO;
import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.request.MessageRequest;
import com.vovafomin.insidechallenge.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("messages")
public class MessageController {

  private final MessageService messageService;

  @Autowired
  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  @PostMapping
  public ResponseEntity<?> post(
      @Valid @RequestBody MessageRequest body, @AuthenticationPrincipal User user) {
    Optional<List<MessageDTO>> messages = messageService.process(body, user);
    if (messages.isPresent()) {
      return ResponseEntity.ok(messages.get());
    } else {
      return ResponseEntity.ok().build();
    }
  }
}
