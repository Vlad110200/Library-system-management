package com.vladl.library.mapper;

import com.vladl.library.dto.LoanDto;
import com.vladl.library.model.Book;
import com.vladl.library.model.Loan;
import com.vladl.library.model.User;

public class LoanMapper {

    public static LoanDto toDto(Loan loan){
        LoanDto dto = new LoanDto();
        dto.id = loan.getId();
        dto.bookId = loan.getBook().getId();
        dto.userId = loan.getUser().getId();
        dto.borrowDate = loan.getBorrowDate();
        dto.returnDate = loan.getReturnDate();
        dto.status = loan.getStatus();
        return dto;
    }

    public static Loan toEntity(LoanDto dto, Book book, User user){
        return new Loan(
                dto.getId(),
                book,
                user,
                dto.getBorrowDate(),
                dto.getReturnDate(),
                dto.getStatus()
        );
    }
}
