package com.travelport.persistence;

import com.travelport.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {

  private EntityManager entityManager;
  private SessionFactory sessionFactory;
  private EntityManagerFactory entityManagerFactory;

  @Override
  public void save(User user) {
    /*var transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();*/

    //var sf = entityManagerFactory.unwrap(SessionFactory.class);

    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();
    session.persist(user);
    tx.commit();
    session.close();
  }

  @Override
  public List<User> list() {
    //JPQL
    return sessionFactory.openSession().createQuery("from User", User.class).getResultList();
  }

  //Setters dependency injection

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Autowired
  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Autowired
  public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }
}
