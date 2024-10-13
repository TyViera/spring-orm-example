package com.travelport.jpa;

import com.travelport.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Integer> {}
