package com.example.SpringDataJDBC.service;

import com.example.SpringDataJDBC.UserNotFoundException;
import com.example.SpringDataJDBC.UserRepository;
import com.example.SpringDataJDBC.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> findAll(){
    return userRepository.findAll();
  }

  public User getUserByUsername(String username){
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException("User not found with username : " + username));
  }

  public void deleteUser(long id){
    userRepository.deleteById(id);
  }

  public User updateUser(User user, long id){
    return userRepository
        .findById(id)
        .map(u -> userRepository.update(user))
        .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
  }

}
