package com.travelport.persistence;

import java.util.List;

public interface AbstractEntityDao<E> {

  void save(E entity);

  List<E> list();

}
