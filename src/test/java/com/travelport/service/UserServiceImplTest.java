package com.travelport.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.travelport.entities.User;
import com.travelport.persistence.UserDao;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  //Under test
  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserDao userDao;

  @Test
  void test() {
    var idToSend = 1;
    User userToSend = null;

    var result = userService.update(idToSend, userToSend);
    assertNull(result);
  }

  @Test
  void test2() {
    Integer idToSend = 1;
    User userToSend = new User();
    userToSend.setName("Bla bla");

    //Mockito.when(userDao.getUserById(ArgumentMatchers.eq(1))).thenReturn(Optional.empty());
    //Mockito.when(userDao.getUserById(ArgumentMatchers.any())).thenReturn(Optional.of(new User()));

    //Mockito.when(userDao.getUserById(ArgumentMatchers.any())).thenThrow(new RuntimeException("Something happened"));

    Mockito.when(userDao.getUserById(ArgumentMatchers.any())).thenAnswer(invocation -> {
      var id = invocation.getArgument(0);
      if (Objects.equals(id, 10)) {
        throw new RuntimeException("Something happened");
      }
      return Optional.of(new User());
    });

    var result = userService.update(idToSend, userToSend);
    assertEquals(userToSend, result);

    Mockito.verify(userDao).getUserById(1);
    Mockito.verify(userDao, Mockito.never()).getUserById(10);
  }
  @Test
  void test3() {
    Integer idToSend = 1;
    User userToSend = new User();
    //userToSend.setName("Bla bla");

    Mockito.when(userDao.getUserById(1)).thenReturn(Optional.of(new User()));

    var result = userService.update(idToSend, userToSend);
    //assertEquals(userToSend, result);
    assertNull(result);

    Mockito.verify(userDao).getUserById(1);
    Mockito.verify(userDao, Mockito.never()).getUserById(10);
    Mockito.verify(userDao, Mockito.never()).update(ArgumentMatchers.any());
  }

}