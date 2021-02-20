package com.solomka.libraryservice;

import com.solomka.libraryservice.models.Author;
import com.solomka.libraryservice.models.Book;
import com.solomka.libraryservice.models.User;
import com.solomka.libraryservice.repository.AuthorRepository;
import com.solomka.libraryservice.repository.BookRepository;
import com.solomka.libraryservice.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class Homework20SourceServiceApplication {


    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public Homework20SourceServiceApplication(
            BookRepository bookRepository,
            UserRepository userRepository,
            AuthorRepository authorRepository
    ) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Homework20SourceServiceApplication.class, args);
    }

    @PostConstruct
    public void addBooks(){
        User oksana = new User();
        oksana.setName("Oksana Solomka");
        oksana.setEmail("oksanasolomka@mail.com");

        Book mobyDick = new Book();
        mobyDick.setTitle("Moby Dick");
        mobyDick.setYearOfPublication(2007);
        mobyDick.setTaken(true);

        Author melville = new Author();
        melville.setName("Herman Melville");

        Book solitude = new Book();
        solitude.setTitle("One Hundred Years of Solitude");
        solitude.setYearOfPublication(1998);
        solitude.setTaken(true);

        Author marquez = new Author();
        marquez.setName("Gabriel Garcia Marquez");

        Book boneshaker = new Book();
        boneshaker.setTitle("Boneshaker");
        boneshaker.setYearOfPublication(2020);
        boneshaker.setTaken(false);

        Author priest = new Author();
        priest.setName("Cherie Priest");

        Book dayOne = new Book();
        dayOne.setTitle("Day One");
        dayOne.setYearOfPublication(2011);
        dayOne.setTaken(false);

        Author devos = new Author();
        devos.setName("Kelly deVos");

        User oksanaFromDB = userRepository.save(oksana);
        mobyDick.setUser(oksanaFromDB);
        Book mobyDickFomDB = bookRepository.save(mobyDick);
        melville.setBook(mobyDickFomDB);

        solitude.setUser(oksanaFromDB);
        Book solitudeFromDB = bookRepository.save(solitude);
        marquez.setBook(solitudeFromDB);

        Book boneshakerFromDB = bookRepository.save(boneshaker);
        priest.setBook(boneshakerFromDB);

        Book dayOneFromDB = bookRepository.save(dayOne);
        devos.setBook(dayOneFromDB);

        authorRepository.saveAll(List.of(melville,marquez,priest,devos));
    }
}
