package com.solomka.libraryservice.repository;

import com.solomka.libraryservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
