package com.travelport.persistence.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import com.travelport.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserDaoImplTest {

  @InjectMocks
  UserDaoImpl userDao;

  @Mock
  EntityManager entityManager;

  @Spy
  HashMap<Integer, User> cache;

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

  @ParameterizedTest
  @MethodSource("provideStringsForIsUserExist")
  void givenParamValues_whenIsUserExists_ThenReturnAssertValue(String name, boolean assertValue) {
    // Test something
    var result = userDao.isUserExist(name);
    assertEquals(assertValue, result);
  }

  //List tests
  @Test
  @SuppressWarnings("unchecked")
  @DisplayName("Given entity manager query returns empty for Naz and Toyota values When list Then return empty list")
  void listTestSuccessEmpty() {
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

  @Test
  void getUserByIdTest() {
    //Set up - Given
    var idToFind = 10;

    ReflectionTestUtils.setField(userDao, "cache", cache);
    Mockito.when(entityManager.find(User.class, idToFind)).thenReturn(new User());

    assertTrue(cache.isEmpty());//Before the call, the cache must be empty
    //Do something - When
    var result = userDao.getUserById(idToFind);
    //asserts - Then
    assertNotNull(result);
    assertTrue(result.isPresent());
    assertFalse(cache.isEmpty());//After the call, the cache must have the value

    Mockito.verify(entityManager).find(User.class, idToFind);
  }

  @Test
  void getUserByIdSpyTest() {
    //Set up - Given
    var idToFind = 10;
    var cacheUser = new User();
    cacheUser.setName("cache-value");

    cache.put(idToFind, cacheUser);
    ReflectionTestUtils.setField(userDao, "cache", cache);

    //Do something - When
    var result = userDao.getUserById(idToFind);
    //asserts - Then
    assertNotNull(result);
    assertTrue(result.isPresent());
    assertEquals(cacheUser, result.get());

    Mockito.verify(entityManager, Mockito.never()).find(User.class, idToFind);
  }

  static Stream<Arguments> provideStringsForIsUserExist() {
    return Stream.of(
        Arguments.of(null, false),
        Arguments.of("", false),
        Arguments.of("agusdiaiusdasidb", false),
        Arguments.of("naz", false),
        Arguments.of("Naz", true)
    );
  }

}