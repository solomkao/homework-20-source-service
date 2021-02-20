package com.solomka.libraryservice.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private String name;

    @Column
    private String email;

//    @OneToMany(mappedBy="user")
//    List<Book> books = new ArrayList<>();
//
//
//    public void addBook(Book book){
//        books.add(book);
//    }
//    public void addBooks(List<Book> book){
//        books.addAll(book);
//    }

}
