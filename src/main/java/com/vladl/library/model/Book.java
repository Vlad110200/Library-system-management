package com.vladl.library.model;

import java.time.LocalDate;

public class Book {
    private static int counter = 1;

    private int id;
    private String title;
    private String author;
    private BookStatus status;
    private LocalDate libraryPurchaseDate;

    public Book(){
        //only for Jackson
    }

    //Constructor for add new book to the library
    public Book(String title, String author, LocalDate libraryPurchaseDate){
        this.id = counter++;
        this.title = title;
        this.author = author;
        this.status = BookStatus.AVAILABLE;
        this.libraryPurchaseDate = libraryPurchaseDate;
    }

    public void markBorrowed(){
        if(status != BookStatus.AVAILABLE){
            throw new IllegalStateException("Book is not available for borrowing");
        }
        this.status = BookStatus.BORROWED;
    }

    public void markReturned(){
        if(status != BookStatus.BORROWED){
            throw new IllegalStateException("Book is not borrowing");
        }
        this.status = BookStatus.AVAILABLE;
    }

    public void changeBookStatus(int bookId, BookStatus status){
        if(status == BookStatus.REMOVED && getStatus() == BookStatus.BORROWED){
            throw new IllegalStateException("Cannot delete borrowed book");
        }

        if(status == BookStatus.AVAILABLE && getStatus() == BookStatus.BORROWED){
            throw new IllegalStateException("Cannot change status to available, book is borrowed");
        }

        if(status == getStatus()){
            throw new IllegalStateException("Cannot change status book, it is already in this status");
        }

        this.status = status;
    }

    public int getId(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public BookStatus getStatus(){
        return status;
    }

    public LocalDate getLibraryPurchaseDate(){
        return libraryPurchaseDate;
    }

    public static void setCounter(int value){
        counter = value;
    }

    @Override
    public String toString(){
        return String.format(
                "Book{id:%d, title=%s, author=%s, status=%s, libraryPurchaseDate=%s}",
                id,
                title,
                author,
                status,
                libraryPurchaseDate
        );
    }

    //For tests
    public static void resetCounter(){
        counter = 1;
    }
}
