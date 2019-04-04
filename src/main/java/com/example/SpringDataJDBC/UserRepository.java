package com.example.SpringDataJDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class UserRepository {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UserRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<User> findAll() {
    return jdbcTemplate.query("select * from users", (rs, i) -> new User(rs.getLong("id"), rs.getString("name"), rs.getString("username")));

  }

  public Optional<User> findByUsername(String username) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject("select * from users where username =  ?"
          , new Object[]{username}
          , (rs, i) -> new User(rs.getLong("id"), rs.getString("name"), rs.getString("username"))));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public Optional<User> findById(Long id) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject("select * from users where id =  ?"
          , new Object[]{id}
          , (rs, i) -> new User(rs.getLong("id"), rs.getString("name"), rs.getString("username"))));
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  public User store(User user) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement("insert into users (name, username) values (?, ?)", new String[]{"id"});
          ps.setString(1, user.getName());
          ps.setString(2, user.getUsername());
          return ps;
        },
        keyHolder);

    user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
    return user;
  }

  public User update(User user) {

    jdbcTemplate.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement("update users set name = ?, username = ? where id = ?", Statement.NO_GENERATED_KEYS);
          ps.setString(1, user.getName());
          ps.setString(2, user.getUsername());
          ps.setLong(3, user.getId());
          return ps;
        });

    return user;
  }

  public void deleteById(Long id) {
    jdbcTemplate.update("delete from users where id =  ?", id);
  }

}
