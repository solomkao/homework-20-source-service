package com.solomka.libraryservice.services;

import com.solomka.libraryservice.exceptions.BookNotFoundException;
import com.solomka.libraryservice.exceptions.BookUnavailableException;
import com.solomka.libraryservice.exceptions.UserNotFoundException;
import com.solomka.libraryservice.models.Book;
import com.solomka.libraryservice.models.User;
import com.solomka.libraryservice.models.dtos.BookDto;
import com.solomka.libraryservice.models.dtos.CreateBookDto;
import com.solomka.libraryservice.repository.AuthorRepository;
import com.solomka.libraryservice.repository.BookRepository;
import com.solomka.libraryservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookService(BookRepository bookRepository, UserRepository userRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public List<Book> findBooksByUserId(long userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return bookRepository.findBooksByUserUserId(userId);
    }

    public Book findBookById(long bookId) {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    public Book saveBook(Book book) {
        return  bookRepository.save(book);
    }

    public Book findBookByTitle(String title) {
        return bookRepository.findByTitle(title).orElseThrow(BookNotFoundException::new);
    }

    public Book takeBook(String userId, BookDto bookDto) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findByTitle(bookDto.getTitle()).orElseThrow(BookNotFoundException::new);
        if(book.isTaken()){
            throw new BookUnavailableException();
        }
        book.setTaken(true);
        book.setUser(user);
        Book takenBook = bookRepository.save(book);
        userRepository.save(user);
        return takenBook;
    }

    public Book returnBook(String userId, BookDto bookDto) {
        User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(UserNotFoundException::new);
        Book book = bookRepository.findByTitle(bookDto.getTitle()).orElseThrow(BookNotFoundException::new);
        if(!book.isTaken()){
            throw new BookUnavailableException();
        }
        if(!book.getUser().getUserId().equals(user.getUserId())){
            throw new BookUnavailableException();
        }
        book.setTaken(false);
        book.setUser(null);
        Book takenBook = bookRepository.save(book);
        userRepository.save(user);
        return takenBook;
    }

    public Book addBook(CreateBookDto createBookDto){
        Book book = new Book();
        book.setTitle(createBookDto.getTitle());
        book.setYearOfPublication(createBookDto.getYearOfPublication());
        book.setAuthors(createBookDto.getAuthors());
        book.setTaken(false);
        Book addedBook = bookRepository.save(book);
        for (int i = 0; i < createBookDto.getAuthors().size(); i++) {
            createBookDto.getAuthors().get(i).setBook(addedBook);
        }
       authorRepository.saveAll(createBookDto.getAuthors());
        return addedBook;
    }
}
