package com.todolistrest.controllers;

import com.todolistrest.dto.UserDto;
import com.todolistrest.entities.User;
import com.todolistrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping(value = "/users")
    public UserDto save(@Valid @RequestBody User user) {
        User dbUser = userService.save(user);
        UserDto userDto = new UserDto();
        userDto.setUsername(dbUser.getUsername());
        return userDto;
    }


}
