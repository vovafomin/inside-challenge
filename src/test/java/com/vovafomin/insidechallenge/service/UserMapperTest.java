package com.vovafomin.insidechallenge.service;

import com.vovafomin.insidechallenge.entity.User;
import com.vovafomin.insidechallenge.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserMapperTest {

  @Test
  public void mapUserReturnsValidDTO() {
    User user =
        User.builder()
            .id(UUID.randomUUID().toString())
            .username("test_user")
            .password("test_password")
            .build();
    var dto = new UserMapper().mapUser(user);
    assertThat(dto).usingRecursiveComparison().isEqualTo(user);
  }
}
