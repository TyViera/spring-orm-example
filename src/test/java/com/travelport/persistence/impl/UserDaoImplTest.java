package com.travelport.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

  @InjectMocks
  UserDaoImpl userDao;

  @Mock
  EntityManager entityManager;

  @BeforeAll
  static void beforeAll() {
    System.out.println("Before all...");
  }

  @BeforeEach
  void initEachTest() {
    System.out.println("Init test...");
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

  //List tests
  @Test
  void testList() {
    //Set up - Given
    var jpql = "from User u join u.cars c WHERE u.name like :name AND c.brand like :carName";

    var mockTypeQuery = Mockito.mock(TypedQuery.class);
    Mockito.when(entityManager.createQuery(eq(jpql), any())).thenReturn(mockTypeQuery);

    Mockito.when(mockTypeQuery.setParameter("name", "Naz")).thenReturn(mockTypeQuery);
    Mockito.when(mockTypeQuery.setParameter("carName", "Toyota")).thenReturn(mockTypeQuery);
    Mockito.when(mockTypeQuery.getResultList()).thenReturn(List.of());

    //Do something - When
    var result = userDao.list("Naz", "Toyota");

    //asserts - Then
    assertNotNull(result);
    assertTrue(result.isEmpty());

    Mockito.verify(entityManager).createQuery(eq(jpql), any());
  }

}