package com.travelport.service;

import com.travelport.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

  List<User> findByNameOrCarBrand(String name, String cardBrand);

  Optional<User> findById(Integer id);

  void deleteById(Integer id);

  User save(User user);

  User update(Integer id, User user);

}
