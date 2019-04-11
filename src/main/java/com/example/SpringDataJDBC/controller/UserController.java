package com.example.SpringDataJDBC.controller;

import com.example.SpringDataJDBC.UserRepository;
import com.example.SpringDataJDBC.entity.User;
import com.example.SpringDataJDBC.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @GetMapping(value = "/users")
    public List<User> getAllUser() {
        return userService.findAll();
    }

    @RequestMapping(value = "/findUsers" ,method = RequestMethod.GET,produces = {"application/json"})
    public User getUserByUsername(@RequestParam(value = "username",required = false) String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping(value = "/users",produces = {"application/json"})
    public User saveUser(@RequestBody User user) {

        return userService.saveUser(user);
    }

    @PutMapping(value = "/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable Long id) {
         return userService.updateUser(user,id);

    }

    @DeleteMapping(value = "/users/{id}")
    public User deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

}
