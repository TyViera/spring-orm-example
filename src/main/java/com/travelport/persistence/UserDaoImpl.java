package com.travelport.persistence;

import com.travelport.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDaoImpl implements UserDao {

  private EntityManager entityManager;

  /**
   * BEGIN
   * -- 2 records
   * INSERT INTO users (name, country) VALUES ('Naz', 'Peru');
   * -- 3 records
   * SELECT * FROM users; -- 3
   * commit
   * <p>
   * SELECT * FROM users; -- 3
   */
  @Override
  @Transactional(isolation = Isolation.READ_COMMITTED, transactionManager = "")
  public void save(User user) {
    if (user.getCars() != null && !user.getCars().isEmpty()) {
      user.getCars().forEach(x -> x.setUser(user));
    }
    entityManager.persist(user);
  }

  @Override
  @Transactional(readOnly = true, propagation = Propagation.NEVER)
  public List<User> list(String name, String carName) {
    //JPQL
    //select * from users where name like ?
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
      jpql = "from User";
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

  //Setters dependency injection

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
}
