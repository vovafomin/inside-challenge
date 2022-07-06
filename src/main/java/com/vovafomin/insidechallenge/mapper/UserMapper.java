package com.vovafomin.insidechallenge.mapper;

import com.vovafomin.insidechallenge.dto.UserDTO;
import com.vovafomin.insidechallenge.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

  public UserDTO mapUser(User user) {
    return UserDTO.builder().id(user.getId()).username(user.getUsername()).build();
  }
}
