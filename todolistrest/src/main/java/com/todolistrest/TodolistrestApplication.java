package com.todolistrest;

import com.todolistrest.entities.Role;
import com.todolistrest.entities.RoleTypes;
import com.todolistrest.entities.User;
import com.todolistrest.services.RoleService;
import com.todolistrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodolistrestApplication implements CommandLineRunner {

	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(TodolistrestApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Metoda ce ruleaza in momentul in care aplicatia porneste
		System.out.println("Inserting roles in database");
		Role userRole = new Role();
		userRole.setName(RoleTypes.USER);
		userRole.setDescription("This role is designed for users");

		try {
			roleService.save(userRole);
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
		}

		Role userRole1 = new Role();
		userRole1.setName(RoleTypes.USER);
		userRole1.setDescription("Duplicate role");

		try {
			roleService.save(userRole1);
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
		}


		Role adminRole = new Role();
		adminRole.setName(RoleTypes.ADMIN);
		adminRole.setDescription("This role can delete resources - tasks & users");

		try {
			roleService.save(adminRole);
		} catch (RuntimeException ex) {
			System.out.println(ex.getMessage());
		}

		User adminUser = new User();
		adminUser.setUsername("admin");
		adminUser.setPassword("admin");

		Role adminRoleInDatbase = roleService.findByName(RoleTypes.ADMIN);
		adminUser.setRole(adminRoleInDatbase);

		try {
			userService.save(adminUser);
		} catch (RuntimeException ex) {
			System.out.println("User admin is already in the database");
		}
	}
}
