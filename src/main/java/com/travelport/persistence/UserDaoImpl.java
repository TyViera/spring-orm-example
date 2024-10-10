package com.travelport.persistence;

import com.travelport.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
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
  @Transactional(isolation = Isolation.READ_COMMITTED)
  public void save(User user) {
    if (user.getCars() != null && !user.getCars().isEmpty()) {
      user.getCars().forEach(x -> x.setUser(user));
    }
    entityManager.persist(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> list() {
    //JPQL
    return entityManager.createQuery("from User", User.class).getResultList();
  }

  //Setters dependency injection

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
}
