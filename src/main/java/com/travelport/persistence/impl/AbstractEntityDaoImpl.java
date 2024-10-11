package com.travelport.persistence.impl;

import com.travelport.persistence.AbstractEntityDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class AbstractEntityDaoImpl<E> implements AbstractEntityDao<E> {

  @PersistenceContext
  private EntityManager entityManager;
  private Class<E> persistentClass;

  protected AbstractEntityDaoImpl() {
    this.persistentClass = (Class<E>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  @Override
  public void save(E entity) {
    entityManager.persist(entity);
  }

  @Override
  public List<E> list() {
    return entityManager.createNamedQuery("from " + persistentClass.getName(), persistentClass).getResultList();
  }
}
