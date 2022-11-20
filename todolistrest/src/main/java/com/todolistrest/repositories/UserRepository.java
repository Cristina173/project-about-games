package com.todolistrest.repositories;

import com.todolistrest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// JpaRepository<TipulClasei, TipulCheiiPrimareAClasei>
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
