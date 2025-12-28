package com.vladl.library.menu;

import com.vladl.library.model.BookStatus;
import com.vladl.library.model.UserStatus;
import com.vladl.library.service.BookService;
import com.vladl.library.service.LoanService;
import com.vladl.library.service.UserService;

import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);

    private final BookService bookService;
    private final LoanService loanService;
    private final UserService userService;

    public ConsoleMenu(
            BookService bookService,
            UserService userService,
            LoanService loanService
    ){
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    public void start(){
        boolean running = true;

        while(running){
            printMenu();
            int choice = readMenuChoice();

            try {
                switch(choice) {
                    case 1 -> showBooks();
                    case 2 -> showUsers();
                    case 3 -> showActiveLoans();
                    case 4 -> showAllLoans();
                    case 5 -> addBook();
                    case 6 -> addUser();
                    case 7 -> borrowBook();
                    case 8 -> returnBook();
                    case 9 -> markRemovedBook();
                    case 10 -> markDeletedUser();
                    case 11 -> removeLoan();
                    case 12 -> markAvailableBook();
                    case 13 -> markActiveUser();
                    case 0 -> running = false;
                    default -> System.out.println("Unknown options");
                }
            } catch(Exception e){
                System.out.println("❌ Error\n" + e.getMessage());
            }
        }
        System.out.println("GoodBye!");
    }

    public void printMenu(){
        System.out.println("""
                
                ==== LIBRARY SYSTEM ====
                1. Show all books
                2. Show all users
                3. Show active loans
                4. Show all loans
                5. Add a book
                6. Add a user
                7. Borrow a book
                8. Return a book
                9. Mark removed a book
                10. Mark deleted a user
                11. Remove a loan
                12. Mark available a book
                13. Mark active a user
                0. Exit
                """);
    }

    private void addBook(){
        System.out.println("=== Add book ===");
        String title = readNonEmptyString("Enter title: ");

        String author = readNonEmptyString("Enter author: ");

        bookService.addBook(title, author);
        System.out.println("✅ Book successfully added\n");
    }

    private void showBooks(){
        System.out.println("=== All books ===");
        var books = bookService.getAllBooks();

        if(books.isEmpty()){
            System.out.println("No books found");
        }

        books.forEach(System.out::println);
    }

    private void showUsers(){
        System.out.println("=== All users ===");
        var users = userService.getAllUsers();

        if(users.isEmpty()){
            System.out.println("No users found");
        }

        users.forEach(System.out::println);
    }

    private void markRemovedBook(){
        System.out.println("=== Mark remove a book ===");
        int bookId = readPositiveInt("Book ID: ");

        bookService.changeBookStatus(bookId, BookStatus.REMOVED);
        System.out.println("✅ Book is successfully mark removed\n");
    }

    private void markAvailableBook(){
        System.out.println("=== Mark available a book ===");
        int bookId = readPositiveInt("Book ID: ");

        bookService.changeBookStatus(bookId, BookStatus.AVAILABLE);
        System.out.println("✅ Book is successfully mark available\n");
    }

    private void markDeletedUser(){
        System.out.println("=== Mark deleted a user ===");
        int userId = readPositiveInt("User ID: ");

        userService.changeUserStatus(userId, UserStatus.DELETED);
        System.out.println("✅ User is successfully mark deleted\n");
    }

    private void markActiveUser(){
        System.out.println("=== Mark active a user ===");
        int userId = readPositiveInt("User ID: ");

        userService.changeUserStatus(userId, UserStatus.ACTIVE);
        System.out.println("✅ User is successfully mark active\n");
    }

    private void removeLoan(){
        System.out.println("=== Remove loan ===");
        int loanId = readPositiveInt("Loan ID: ");

        loanService.removeLoan(loanId);
        System.out.println("✅ Loan is successfully removed\n");
    }

    private void addUser(){
        System.out.println("=== Add user ===");
        String name = readNonEmptyString("Name: ");

        userService.addUser(name);
        System.out.println("✅ User successfully added\n");
    }

    private void borrowBook(){
        System.out.println("=== Borrow book ===");
        int bookId = readPositiveInt("Book ID: ");
        int userId = readPositiveInt("User ID: ");
        int days = readPositiveInt("Days: ");

        loanService.borrowBook(bookId, userId, days);
        System.out.println("✅ Book successfully borrowed\n");
    }

    private void returnBook(){
        System.out.println("=== Return book ===");
        int bookId = readPositiveInt("Book ID: ");

        loanService.returnBook(bookId);
        System.out.println("✅ Book successfully returned\n");
    }

    private void showActiveLoans(){
        System.out.println("=== All active loans ===");
        var loans = loanService.getActiveLoans();

        if(loans.isEmpty()){
            System.out.println("Active loans not found");
        }

        loans.forEach(System.out::println);
    }

    private void showAllLoans(){
        System.out.println("=== All loans ===");
        var loans = loanService.getAllLoans();

        if(loans.isEmpty()){
            System.out.println("Loans not found");
        }

        loans.forEach(System.out::println);
    }

    private String readNonEmptyString(String prompt){
        while(true){
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if(!input.isEmpty()){
                return input;
            }
            System.out.println("❌ Input cannot be empty. Try again");
        }
    }

    private int readPositiveInt(String prompt){
        while(true){
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine());
                if(value >= 0){
                    return value;
                }
                System.out.println("❌ Enter 0 or positive number");
            } catch (NumberFormatException e){
                System.out.println("❌ Invalid number format");
            }
        }
    }

    private int readMenuChoice(){
        return readPositiveInt("Choose option: ");
    }
}
