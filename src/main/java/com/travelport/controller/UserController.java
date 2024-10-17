package com.travelport.controller;

import com.travelport.entities.User;
import com.travelport.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public List<User> getUsers(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "car", required = false) String carName) {
    return userService.findByNameOrCarBrand(name, carName);
  }

  @PostMapping
  public User postUser(@RequestBody User user) {
    return userService.save(user);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
    var user = userService.findById(id);
    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PatchMapping("/{id}")
  public ResponseEntity<User> updateById(@PathVariable("id") Integer id, @RequestBody User user) {
    return ResponseEntity.ok(userService.update(id, user));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
    userService.deleteById(id);
    return ResponseEntity.noContent().build();
  }
}
