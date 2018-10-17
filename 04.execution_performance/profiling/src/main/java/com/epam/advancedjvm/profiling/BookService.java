package com.epam.advancedjvm.profiling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    public BookService(@Autowired BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> findBooks() {
        List<Book> books = repository.findBooks();
        books.forEach(book -> book.setContent(applyCensorship(repository.findContent(book))));
        return books;
    }

    //Today we are in a black mood -> paint everything with black
    private String applyCensorship(String content) {
        for (String color: new String[] {"red", "green", "blue", "yellow", "white", "gray", "pink", "beige", "violet", "cyan"}) {
            content = content.replaceAll(color, "black");
        }
        return content;
    }
}
