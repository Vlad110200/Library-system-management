package com.vladl.library.repository.json;

import com.vladl.library.model.Book;
import com.vladl.library.repository.BookRepository;
import com.vladl.library.storage.JsonStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JsonBookRepository implements BookRepository {

    private final List<Book> books;
    private final String FILE = "books.json";

    public JsonBookRepository(){
        this.books = new ArrayList<>(
                JsonStorage.load(FILE, Book.class)
        );
        int maxId = books.stream()
                .mapToInt(Book::getId)
                .max()
                .orElse(0);
        Book.setCounter(maxId + 1);
    }

    @Override
    public void save(Book book){
        books.add(book);
        persist();
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
        JsonStorage.save(FILE, books);
    }
}
