package com.travelport.controller;

import com.travelport.entities.User;
import com.travelport.persistence.UserDao;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserDao userDao;

  public UserController(UserDao userDao) {
    this.userDao = userDao;
  }

  @GetMapping
  public List<User> getUsers(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "car", required = false) String carName) {
    // Message Converter - Jackson / GSON
    // id- name- country - cars
    // getId() - getName() - getCountry() - getCars()
    return userDao.list(name, carName);
  }

  @PostMapping
  public User postUser(@RequestBody User user) {

    /**
     * user.name = Jil user.country = Peru user.cars[0].brand = Toyota user.cars[0].user = null
     */
    userDao.save(user);
    return user;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Integer id) {
    var user = userDao.getUserById(id);

    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    /*
    if (user.isPresent()) {
       return ResponseEntity.ok(user.get());
    }
    return ResponseEntity.notFound().build();
    */
  }

  @PatchMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Integer id, @RequestBody User user) {
    var findUser = userDao.getUserById(id);
    if (findUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    findUser.get().setName(user.getName());
    userDao.update(findUser.get());
    return ResponseEntity.ok(findUser.get());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) {
    var user = userDao.deleteById(id);
    return user.map(u -> ResponseEntity.noContent().<Void>build())
        .orElseGet(() -> ResponseEntity.notFound().build());
    /*
    if (user.isPresent()) {
       return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
    */
  }

}
