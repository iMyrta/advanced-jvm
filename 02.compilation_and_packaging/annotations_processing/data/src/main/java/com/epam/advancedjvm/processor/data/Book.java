package com.epam.advancedjvm.processor.data;

import com.epam.advancedjvm.processor.ProtobufData;

@ProtobufData
public class Book {

    private String isbn;
    private String author;
    private int year;


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
