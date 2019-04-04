package com.example.SpringDataJDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UserRepository(JdbcTemplate jdbcTemplate) {

    this.jdbcTemplate = jdbcTemplate;
  }

  public List<User> findAll() {
    return  null;
  }

  public Optional<User> findByUsername(String username) {
    return  null;

  }

  public Optional<User> findById(Long id) {
    return  null;
  }

  public User store(User user) {
    return  null;
  }

  public User update(User user) {

    return  null;
  }

  public void deleteById(Long id) {

  }

}
