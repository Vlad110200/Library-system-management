package com.vladl.library.dto;

import com.vladl.library.model.LoanStatus;

import java.time.LocalDate;

public class LoanDto {

    public int id;
    public int bookId;
    public int userId;

    public LocalDate borrowDate;
    public LocalDate returnDate;

    public LoanStatus status;

    public LoanDto(){

    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public int getBookId(){return bookId;}
    public void setBookId(int bookId){this.bookId = bookId;}

    public int getUserId(){return userId;}
    public void setUserId(int userId){this.userId = userId;}

    public LocalDate getBorrowDate(){return borrowDate;}
    public void setBorrowDate(LocalDate borrowDate){this.borrowDate = borrowDate;}

    public LocalDate getReturnDate(){return returnDate;}
    public void setReturnDate(LocalDate returnDate){this.returnDate = returnDate;}

    public LoanStatus getStatus(){return status;}
    public void setStatus(LoanStatus status){this.status = status;}

}
