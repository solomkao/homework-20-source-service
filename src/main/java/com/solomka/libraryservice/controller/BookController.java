package com.solomka.libraryservice.controller;

import com.solomka.libraryservice.models.Book;
import com.solomka.libraryservice.models.User;
import com.solomka.libraryservice.models.dtos.BookDto;
import com.solomka.libraryservice.models.dtos.CreateBookDto;
import com.solomka.libraryservice.services.BookService;
import com.solomka.libraryservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @Autowired
    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping(value = "/books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<List<Book>> getUserBooks(
            @PathVariable("id") String userId
    ){
        var books = bookService.findBooksByUserId(Long.parseLong(userId));
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping(value = "/books/{id}")
    public ResponseEntity<Book> takeBook(
            @PathVariable("id") String bookId
    ){
        Book book = bookService.findBookById(Long.parseLong(bookId));
        book.setTaken(true);
        bookService.saveBook(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{id}",
                consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> takeBook(
            @PathVariable("id") String userId,
            @RequestBody BookDto bookDto
    ){
        Book takenBook = bookService.takeBook(userId, bookDto);
        return new ResponseEntity<>(takenBook, HttpStatus.OK);
    }

    @PostMapping(value = "/users/return/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> returnBook(
            @PathVariable("id") String userId,
            @RequestBody BookDto bookDto
    ){
        Book takenBook = bookService.returnBook(userId, bookDto);
        return new ResponseEntity<>(takenBook, HttpStatus.OK);
    }

    @PostMapping(value = "/books",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> addNewBook(
            @RequestBody CreateBookDto createBookDto
    ){
        Book addedBook = bookService.addBook(createBookDto);
        return new ResponseEntity<>(addedBook, HttpStatus.OK);
    }
}
