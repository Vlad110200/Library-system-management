package com.vladl.library.service;

import com.vladl.library.model.*;
import com.vladl.library.repository.BookRepository;
import com.vladl.library.repository.LoanRepository;
import com.vladl.library.repository.UserRepository;
import com.vladl.library.repository.memory.InMemoryBookRepository;
import com.vladl.library.repository.memory.InMemoryLoanRepository;
import com.vladl.library.repository.memory.InMemoryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanServiceTest {

    private LoanService loanService;
    private BookService bookService;
    private UserService userService;

    @BeforeEach
    void setup(){
        BookRepository bookRepository = new InMemoryBookRepository();
        UserRepository userRepository = new InMemoryUserRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();

        Book.resetCounter();
        User.resetCounter();
        Loan.resetCounter();

        bookService = new BookService(bookRepository, loanRepository);
        userService = new UserService(userRepository, loanRepository);
        loanService = new LoanService(loanRepository, bookService, userService);
    }

    @Test
    void borrowBook_success(){
        bookService.addBook("Test_title", "Test_author");
        userService.addUser("Test_name");

        Book book = bookService.getAllBooks().getFirst();
        User user = userService.getAllUsers().getFirst();

        loanService.borrowBook(book.getId(), user.getId(), 7);

        Book updateBook = bookService.getById(book.getId());
        assertEquals(BookStatus.BORROWED, updateBook.getStatus());
    }

    @Test
    void borrowBook_twice_shouldThrowException(){
        bookService.addBook("Test_title", "Test_author");
        userService.addUser("Test_name");

        Book book = bookService.getAllBooks().getFirst();
        User user = userService.getAllUsers().getFirst();

        loanService.borrowBook(book.getId(), user.getId(), 7);

        assertThrows(IllegalArgumentException.class, () ->
                loanService.borrowBook(book.getId(), user.getId(), 7));
    }

    @Test
    void borrowBook_bookNotFound_shouldThrowException(){
        userService.addUser("Test_name");

        User user = userService.getAllUsers().getFirst();
        int idNonExistingBook = 999;

        assertThrows(IllegalArgumentException.class, () ->
            loanService.borrowBook(idNonExistingBook, user.getId(), 5));
    }

    @Test
    void borrowBook_userNotFound_shouldThrowException(){
        bookService.addBook("Test_title", "Test_author");

        Book book = bookService.getAllBooks().getFirst();
        int nonExistingUserId = 999;

        assertThrows(IllegalArgumentException.class, () ->
                loanService.borrowBook(book.getId(), nonExistingUserId, 5));
    }

    @Test
    void borrowBook_deletedUser_shouldThrowException(){
        bookService.addBook("Test_title", "Test_author");
        userService.addUser("Test_name");

        Book book = bookService.getAllBooks().getFirst();
        User user = userService.getAllUsers().getFirst();

        user.changeUserStatus(user.getId(), UserStatus.DELETED);

        assertThrows(IllegalStateException.class, () ->
                loanService.borrowBook(book.getId(), user.getId(), 6));
    }

    @Test
    void returnBook_success(){
        bookService.addBook("Test_title", "Test_author");
        userService.addUser("Test_name");

        Book book = bookService.getAllBooks().getFirst();
        User user = userService.getAllUsers().getFirst();

        loanService.borrowBook(book.getId(), user.getId(), 5);
        loanService.returnBook(book.getId());

        Book updateBook = bookService.getById(book.getId());
        assertEquals(BookStatus.AVAILABLE, updateBook.getStatus());
    }

    @Test
    void returnBook_twice_shouldThrowException(){
        bookService.addBook("Test_title", "Test_author");
        userService.addUser("Test_name");

        Book book = bookService.getAllBooks().getFirst();
        User user = userService.getAllUsers().getFirst();

        loanService.borrowBook(book.getId(), user.getId(), 5);
        loanService.returnBook(book.getId());

        assertThrows(IllegalArgumentException.class, () ->
                loanService.returnBook(book.getId()));
    }

    @Test
    void returnBook_bookNotBorrowed_shouldThrowException(){
        bookService.addBook("Test_title", "Test_author");

        Book book = bookService.getAllBooks().getFirst();

        assertThrows(IllegalArgumentException.class, () ->
                loanService.returnBook(book.getId()));
    }

    @Test
    void returnBook_bookNotFound_shouldThrowException(){
        int nonExistingBookId = 999;

        assertThrows(IllegalArgumentException.class, () ->
                loanService.returnBook(nonExistingBookId));
    }

    @Test
    void returnBook_shouldSetStatusLoanReturned(){
        bookService.addBook("Test_title", "Test_author");
        userService.addUser("Test_name");

        Book book = bookService.getAllBooks().getFirst();
        User user = userService.getAllUsers().getFirst();

        loanService.borrowBook(book.getId(), user.getId(), 6);
        loanService.returnBook(book.getId());

        Loan loan = loanService.getAllLoans().getFirst();
        assertEquals(LoanStatus.RETURNED, loan.getStatus());
    }
}
