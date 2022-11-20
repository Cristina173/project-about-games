package com.todolistrest.services;

import com.todolistrest.entities.User;
import com.todolistrest.exceptions.ResourceNotFoundException;
import com.todolistrest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// UserDetailsService este o clasa din spring folosita in procesul
// de autentificare
@Service
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException(String.format("User with username %s not found", username));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getName().name()));
        UserDetails springUser =
                // full qualifier import
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return springUser;
    }
}
