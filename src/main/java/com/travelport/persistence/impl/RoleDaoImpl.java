package com.travelport.persistence.impl;

import com.travelport.entities.Role;
import com.travelport.persistence.RoleDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class RoleDaoImpl extends AbstractEntityDaoImpl<Role> implements RoleDao {

}
