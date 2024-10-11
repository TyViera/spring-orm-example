package com.travelport.persistence;

import com.travelport.entities.Car;
import java.util.List;

public interface CarDao {
  void save(Car car);

  List<Car> list();
}
