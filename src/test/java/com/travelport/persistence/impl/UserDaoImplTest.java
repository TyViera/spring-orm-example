package com.travelport.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoImplTest {

  UserDaoImpl userDao;

  /*
  var data = fetchData();
  var result = userDao.doSomething(data);
  assertNotNUll(result);
   */

  @BeforeAll
  static void beforeAll(){
    System.out.println("Before all...");
  }

  @BeforeEach
  void initEachTest() {
    System.out.println("Init test...");
    userDao = new UserDaoImpl();
  }

  @AfterEach
  void afterEachTest() {
    System.out.println("Cleaning up...");
  }

  @Test
  void myTest() {
    // Test something
    var result = userDao.isUserExist(null);
    assertFalse(result);
  }

  @Test
  void myTest2() {
    // Test something
    var result = userDao.isUserExist("");
    assertFalse(result);
  }

  @Test
  void myTest3() {
    // Test something
    var result = userDao.isUserExist("agusdiaiusdasidb");
    assertFalse(result);
  }

  @Test
  void myTest4() {
    // Test something
    var result = userDao.isUserExist("naz");
    assertFalse(result);
  }

  @Test
  void myTest5() {
    // Test something
    var result = userDao.isUserExist("Naz");
    assertTrue(result);
  }

}