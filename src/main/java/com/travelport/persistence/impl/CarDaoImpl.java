package com.travelport.persistence.impl;

import com.travelport.entities.Car;
import com.travelport.persistence.CarDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CarDaoImpl extends AbstractEntityDaoImpl<Car> implements CarDao {
}
