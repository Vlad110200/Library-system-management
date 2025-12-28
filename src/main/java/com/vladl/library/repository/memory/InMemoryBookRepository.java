package com.vladl.library.repository.memory;

import com.vladl.library.model.Book;
import com.vladl.library.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    @Override
    public void save(Book book){
        books.add(book);
    }

    @Override
    public Optional<Book> getById(int id){
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    @Override
    public List<Book> findAll(){
        return new ArrayList<>(books);
    }

    @Override
    public void persist(){

    }
}
