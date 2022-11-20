package com.todolistrest.repositories;

import com.todolistrest.entities.Role;
import com.todolistrest.entities.RoleTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(RoleTypes name);

}
