package com.vovafomin.insidechallenge.service;

import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.mapper.UserMapper;
import com.vovafomin.insidechallenge.repository.UserRepository;
import com.vovafomin.insidechallenge.request.CreateAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AccountServiceTest {

  @Mock private UserRepository userRepository;
  @Mock private PasswordEncoder passwordEncoder;
  private final UserMapper userMapper = new UserMapper();
  private AccountService accountService;

  @BeforeEach
  public void beforeEach() {
    accountService = new AccountService(passwordEncoder, userRepository, userMapper);
    when(userRepository.save(any(User.class))).thenAnswer(a -> a.getArgument(0));
    when(passwordEncoder.encode(anyString())).thenAnswer(a -> a.getArgument(0));
  }

  @Test
  public void createReturnsValidUser() {
    final String username = "test_user";
    var dto =
        accountService.create(
            CreateAccountRequest.builder().username(username).password("test_password").build());
    assertThat(dto.getUsername()).isEqualTo(username);
  }
}
