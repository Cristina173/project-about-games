package com.todolistrest.services;

import com.todolistrest.entities.Role;
import com.todolistrest.entities.RoleTypes;
import com.todolistrest.entities.User;
import com.todolistrest.repositories.RoleRepository;
import com.todolistrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        //parola in plain text pe care o trimite utilizatorul
        String initialPassword = user.getPassword();
        String encodedPassword = bCryptPasswordEncoder.encode(initialPassword);
        user.setPassword(encodedPassword);
        if (user.getRole() == null){
            // gasim rolul in db
            Role userRole = roleRepository.findRoleByName(RoleTypes.USER);
            // setam rolul utilizatorului
            user.setRole(userRole);
        }
        return userRepository.save(user);
    }

}
