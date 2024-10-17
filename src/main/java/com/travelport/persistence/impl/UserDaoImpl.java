package com.travelport.persistence.impl;

import com.travelport.entities.User;
import com.travelport.persistence.UserDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDaoImpl implements UserDao {

  @PersistenceContext // JPA
  private EntityManager entityManager;

  /**
   * BEGIN -- 2 records INSERT INTO users (name, country) VALUES ('Jil', 'Peru'); -- 3 records
   * SELECT * FROM users; -- 3 commit
   *
   * <p>SELECT * FROM users; -- 3
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void save(User user) {
    if (user.getCars() != null && !user.getCars().isEmpty()) {
      user.getCars().forEach(x -> x.setUser(user));
    }
    entityManager.persist(user);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.NEVER)
  public List<User> list(String name, String carName) {
    return queryUsingJpql(name, carName);
  }

  public boolean isUserExist(String name) {
    return (name != null && name.equals("Naz"));
  }

  private List<User> queryUsingJpql(String name, String carName) {
    // JPQL
    // select * from users where name like ?
    String jpql;
    var receiveName = name != null && !name.isEmpty();
    var receiveCarBrand = carName != null && !carName.isEmpty();
    if (receiveName && receiveCarBrand) {
      jpql = "from User u join u.cars c WHERE u.name like :name AND c.brand like :carName";
    } else if (receiveName) {
      jpql = "from User u WHERE u.name like :name";
    } else if (receiveCarBrand) {
      jpql = "from User u join u.cars c WHERE c.brand like :carName";
    } else {
      jpql = "from User u";
    }

    var query = entityManager.createQuery(jpql, User.class);
    if (receiveName) {
      query.setParameter("name", name);
    }

    if (receiveCarBrand) {
      query.setParameter("carName", carName);
    }

    return query.getResultList();
  }

  private List<User> queryUsingCriteria(String name, String carName) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<User> query = cb.createQuery(User.class);
    Root<User> root = query.from(User.class);
    if (name != null && !name.isEmpty()) {
      query.where(cb.like(root.get("name"), name));
    }
    if (carName != null && !carName.isEmpty()) {
      query.where(cb.like(root.get("cars").get("brand"), carName));
    }
    return entityManager.createQuery(query).getResultList();
  }

  @Override
  public Optional<User> getUserById(Integer id) {
    return Optional.ofNullable(entityManager.find(User.class, id));
  }

  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void update(User user) {
    entityManager.merge(user);
  }

  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public Optional<Integer> deleteById(Integer id) {
    return getUserById(id)
        .map(
            user -> {
              entityManager.remove(user);
              return id;
            });
  }
}
