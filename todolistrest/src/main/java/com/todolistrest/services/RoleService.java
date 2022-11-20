package com.todolistrest.services;

import com.todolistrest.entities.Role;
import com.todolistrest.entities.RoleTypes;
import com.todolistrest.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role save(Role role) {
        Role roleInDatabase = roleRepository.findRoleByName(role.getName());
        if (roleInDatabase != null) {
            throw new RuntimeException("Role already exists");
        } else {
            Role save = roleRepository.save(role);
            return save;
            // return roleRepository.save(role);
        }
    }

    public Role findByName(RoleTypes roleTypes) {
        return roleRepository.findRoleByName(roleTypes);
    }

}
