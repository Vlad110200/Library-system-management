package com.vladl.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan {

    private static int counter = 1;

    private int id;
    private Book book;
    private User user;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    private LoanStatus loanStatus;

    protected Loan(){
        //only for Jackson
    }

    public Loan(
            int id,
            Book book,
            User user,
            LocalDate borrowDate,
            LocalDate returnDate,
            LoanStatus status
    ){
        this.id = id;
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.loanStatus = status;

        if(id >= counter){
            counter = id + 1;
        }
    }

    public Loan(Book book, User user, LocalDate borrowDate, LocalDate returnDate){
        this.id = counter++;
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.loanStatus = LoanStatus.ACTIVE;
    }

    public int getId(){
        return id;
    }

    public Book getBook(){
        return book;
    }

    public User getUser(){
        return user;
    }

    public LocalDate getBorrowDate(){
        return borrowDate;
    }

    public LocalDate getReturnDate(){
        return returnDate;
    }

    public LoanStatus getStatus(){
        return loanStatus;
    }

    public static void setCounter(int value) {
        counter = value;
    }

    @Override
    public String toString(){
        return String.format(
                "Loan{id:%d, bookId:%s, userId:%s, borrowDate:%s, returnDate=%s, status:%s},",
                id,
                book.getId(),
                user.getId(),
                borrowDate,
                returnDate,
                loanStatus
        );
    }

    public static void resetCounter(){
        counter = 1;
    }
}