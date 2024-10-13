package com.travelport.persistence;

import com.travelport.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
  void save(User user);

  List<User> list(String name, String carName);

  Optional<User> getUserById(Integer id);

  void update(User user);

  Optional<Integer> deleteById(Integer id);
}
