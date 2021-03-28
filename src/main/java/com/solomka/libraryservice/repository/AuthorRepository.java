package com.solomka.libraryservice.repository;

import com.solomka.libraryservice.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
