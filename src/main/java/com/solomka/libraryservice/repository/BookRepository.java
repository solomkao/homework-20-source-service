package com.solomka.libraryservice.repository;

import com.solomka.libraryservice.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitle(String title);
    List<Book> findBooksByUserUserId(Long id);
}
