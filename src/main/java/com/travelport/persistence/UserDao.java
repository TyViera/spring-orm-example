package com.travelport.persistence;

import com.travelport.entities.User;
import java.util.List;

public interface UserDao {
  void save(User user);

  List<User> list(String name, String carName);
}
