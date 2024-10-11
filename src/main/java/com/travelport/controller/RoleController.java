package com.travelport.controller;

import com.travelport.entities.Role;
import com.travelport.persistence.RoleDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {

  private final RoleDao roleDao;

  public RoleController(RoleDao roleDao) {
    this.roleDao = roleDao;
  }

  @PostMapping
  public Role postRole(@RequestBody Role role) {
    roleDao.save(role);
    return role;
  }

}
