package com.travelport.persistence;

import com.travelport.entities.Role;
import java.util.List;

public interface RoleDao {
  void save(Role role);

  List<Role> list();
}
