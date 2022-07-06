package com.vovafomin.insidechallenge.service;

import com.vovafomin.insidechallenge.dto.UserDTO;
import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.mapper.UserMapper;
import com.vovafomin.insidechallenge.repository.UserRepository;
import com.vovafomin.insidechallenge.request.CreateAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Autowired
  public AccountService(
      PasswordEncoder passwordEncoder, UserRepository userRepository, UserMapper userMapper) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  public UserDTO create(CreateAccountRequest dto) {
    User user = new User();
    user.setUsername(dto.getUsername());
    user.setPassword(passwordEncoder.encode(dto.getPassword()));
    return userMapper.mapUser(userRepository.save(user));
  }
}
