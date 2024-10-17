package com.travelport.service;

import com.travelport.entities.User;
import com.travelport.persistence.UserDao;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserDao userDao;

  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public List<User> findByNameOrCarBrand(String name, String cardBrand) {
    return userDao.list(name, cardBrand);
  }

  @Override
  public Optional<User> findById(Integer id) {
    return userDao.getUserById(id);
  }

  @Override
  public void deleteById(Integer id) {
    userDao.deleteById(id);
  }

  @Override
  public User save(User user) {
    userDao.save(user);
    return user;
  }

  @Override
  public User update(Integer id, User user) {
    var fuser = userDao.getUserById(id);
    if (fuser.isEmpty()) {
      return null;
    }
    if (user.getName() == null) {
      return null;
    }
    userDao.update(user);
    return user;
  }
}
