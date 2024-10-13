package com.travelport.jpa;

import com.travelport.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarJpaRepository extends JpaRepository<Car, Integer> {}
