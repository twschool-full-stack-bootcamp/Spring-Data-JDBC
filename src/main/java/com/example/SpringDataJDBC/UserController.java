package com.example.SpringDataJDBC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping(value = "/users")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @GetMapping(value = "/users/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username : " + username));
    }

    @PostMapping(value = "/users")
    public User saveUser(@RequestBody User user) {

        return userRepository.store(user);
    }

    @PutMapping(value = "/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
        return userRepository
                .findById(id)
                .map(u -> userRepository.update(user))
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
    }

    @DeleteMapping(value = "/users/{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        return userRepository
                .findById(id)
                .map(u -> {
                    userRepository.deleteById(id);
                    return u;
                })
                .orElseThrow(() -> new UserNotFoundException("User not found with id : " + id));
    }

}
