package com.vladl.library.app;

import com.vladl.library.menu.ConsoleMenu;
import com.vladl.library.repository.BookRepository;
import com.vladl.library.repository.LoanRepository;
import com.vladl.library.repository.UserRepository;
import com.vladl.library.repository.json.JsonBookRepository;
import com.vladl.library.repository.json.JsonLoanRepository;
import com.vladl.library.repository.json.JsonUserRepository;
import com.vladl.library.service.BookService;
import com.vladl.library.service.LoanService;
import com.vladl.library.service.UserService;

public class Main {
    static void main(String[] args){ //args unused
        BookRepository bookRepository = new JsonBookRepository();
        LoanRepository loanRepository = new JsonLoanRepository();
        UserRepository userRepository = new JsonUserRepository();

        BookService bookService = new BookService(bookRepository, loanRepository);
        UserService userService = new UserService(userRepository, loanRepository);
        LoanService loanService = new LoanService(
                loanRepository,
                bookService,
                userService
        );
        new ConsoleMenu(bookService, userService, loanService).start();
    }
}