package com.solomka.libraryservice.models.dtos;

import com.solomka.libraryservice.models.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

    private String title;
    private List<Author> authors = new ArrayList<>();
}
