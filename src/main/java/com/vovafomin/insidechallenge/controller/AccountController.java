package com.vovafomin.insidechallenge.controller;

import com.vovafomin.insidechallenge.dto.UserDTO;
import com.vovafomin.insidechallenge.request.CreateAccountRequest;
import com.vovafomin.insidechallenge.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("accounts")
public class AccountController {

  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public UserDTO create(@Valid @RequestBody CreateAccountRequest body) {
    return accountService.create(body);
  }
}
