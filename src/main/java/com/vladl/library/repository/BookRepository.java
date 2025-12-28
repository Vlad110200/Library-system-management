package com.vladl.library.repository;

import com.vladl.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository{
    void save(Book book);
    Optional<Book> getById(int id);
    List<Book> findAll();
    void persist();
}