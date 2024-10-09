package com.travelport.controller;

import com.travelport.entities.User;
import com.travelport.persistence.UserDao;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserDao userDao;

  public UserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @GetMapping
  public List<User> getUsers() {
    //Message Converter - Jackson / GSON
    return userDao.list();
  }

  @PostMapping
  public User postUser(@RequestBody User user) {
    userDao.save(user);
    return user;
  }
}
