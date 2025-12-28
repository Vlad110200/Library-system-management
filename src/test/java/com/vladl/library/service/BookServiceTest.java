package com.vladl.library.service;

import com.vladl.library.model.Book;
import com.vladl.library.model.BookStatus;
import com.vladl.library.repository.BookRepository;
import com.vladl.library.repository.LoanRepository;
import com.vladl.library.repository.memory.InMemoryBookRepository;
import com.vladl.library.repository.memory.InMemoryLoanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookServiceTest {

    private BookService bookService;

    @BeforeEach
    void setup(){
        BookRepository bookRepository = new InMemoryBookRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();

        Book.resetCounter();

        bookService = new BookService(bookRepository, loanRepository);
    }

    @Test
    void addBook_success(){
        bookService.addBook("Test_title", "test_author");
        Book book = bookService.getAllBooks().getFirst();

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals("Test_title", book.getTitle());
        assertEquals("Test_author", book.getAuthor());
    }

    @Test
    void markRemovedBook_success(){
        bookService.addBook("Test_title", "Test_author");
        Book book = bookService.getAllBooks().getFirst();
        bookService.changeBookStatus(book.getId(), BookStatus.REMOVED);
        assertEquals(BookStatus.REMOVED, book.getStatus());
    }

    @Test
    void markRemovedBook_bookNotFound_shouldThrowException(){
        int nonExistingBookId = 999;
        assertThrows(IllegalArgumentException.class, () ->
                bookService.changeBookStatus(nonExistingBookId, BookStatus.REMOVED));
    }

    @Test
    void getById_success(){
        bookService.addBook("Test_title", "Test_author");
        Book createdBook = bookService.getAllBooks().getFirst();

        Book foundBook = bookService.getById(createdBook.getId());

        assertEquals(createdBook.getId(), foundBook.getId());
        assertEquals("Test_title", foundBook.getTitle());
        assertEquals("Test_author", foundBook.getAuthor());
    }

    @Test
    void getById_bookNotFound_shouldThrowException(){
        int nonExistingBookId = 999;

        assertThrows(IllegalArgumentException.class, () ->
                bookService.getById(nonExistingBookId));
    }

}
