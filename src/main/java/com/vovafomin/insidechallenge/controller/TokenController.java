package com.vovafomin.insidechallenge.controller;

import com.vovafomin.insidechallenge.dto.TokenDTO;
import com.vovafomin.insidechallenge.request.CredentialsRequest;
import com.vovafomin.insidechallenge.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("token")
public class TokenController {

  private final TokenService tokenService;

  @Autowired
  public TokenController(TokenService tokenService) {
    this.tokenService = tokenService;
  }

  @PostMapping
  public TokenDTO exchange(@Valid @RequestBody CredentialsRequest body) {
    return tokenService.exchange(body);
  }
}
