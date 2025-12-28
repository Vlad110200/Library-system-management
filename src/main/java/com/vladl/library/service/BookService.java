package com.vladl.library.service;

import com.vladl.library.model.Book;
import com.vladl.library.model.BookStatus;
import com.vladl.library.repository.BookRepository;
import com.vladl.library.repository.LoanRepository;

import java.time.LocalDate;
import java.util.List;

public class BookService {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    public BookService(BookRepository bookRepository, LoanRepository loanRepository){
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public void addBook(String title, String author){
        Book book = new Book(title, author, LocalDate.now());
        bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public void changeBookStatus(int bookId, BookStatus status){
        Book book = getById(bookId);
        if(loanRepository.hasActiveLoanForBook(bookId) && status == BookStatus.REMOVED){
            throw new IllegalArgumentException(
                    "Cannot change status book with active loans, id=" + bookId);
        }
        book.changeBookStatus(bookId, status);
        bookRepository.persist();
    }

    public Book getById(int bookId){
        return bookRepository.getById(bookId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Book not found with id=" + bookId));
    }

    public Book getByIdOrNull(int id){
        return bookRepository.getById(id).orElse(null);
    }
}
