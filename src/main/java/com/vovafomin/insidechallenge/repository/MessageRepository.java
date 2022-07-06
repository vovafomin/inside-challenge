package com.vovafomin.insidechallenge.repository;

import com.vovafomin.insidechallenge.entity.Message;
import com.vovafomin.insidechallenge.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
  List<Message> findAllByUser(User user);

  List<Message> findAllByUser(User user, Pageable pageable);
}
