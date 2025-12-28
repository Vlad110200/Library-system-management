package com.vladl.library.service;

import com.vladl.library.dto.LoanDto;
import com.vladl.library.mapper.LoanMapper;
import com.vladl.library.model.*;
import com.vladl.library.repository.LoanRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LoanService {

    private final BookService bookService;
    private final LoanRepository loanRepository;
    private final UserService userService;

    public LoanService(
            LoanRepository loanRepository,
            BookService bookService,
            UserService userService
    ){
        this.loanRepository = loanRepository;
        this.bookService = bookService;
        this.userService = userService;
    }

    public void borrowBook(int bookId, int userId, int days){
        Book book = bookService.getById(bookId);
        if(book.getStatus() == BookStatus.REMOVED){
            throw new IllegalStateException("Cannot borrow book with removed book, id=" + bookId);
        }
        User user = userService.getById(userId);
        if(user.getStatus() == UserStatus.DELETED){
            throw new IllegalStateException("Cannot borrow book with deleted user, id=" + userId);
        }

        if(loanRepository.hasActiveLoanForBook(bookId)){
            throw new IllegalArgumentException("Book already borrowed");
        }

        Loan loan = new Loan(
                book,
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(days)
        );

        LoanDto dto = LoanMapper.toDto(loan);
        loanRepository.save(dto);
        book.markBorrowed();
    }

    public void returnBook(int bookId){
        LoanDto dto = loanRepository.findActiveByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Active loan is not found for book id=" + bookId));

        Book book = bookService.getById(bookId);
        User user = userService.getById(dto.getUserId());
        Loan loan = LoanMapper.toEntity(dto, book, user);

        dto.setStatus(LoanStatus.RETURNED);
        dto.setReturnDate(LocalDate.now());
        book.markReturned();
        loanRepository.persist();
    }

    public void removeLoan(int id){
        if(loanRepository.hasActiveLoanForLoan(id)){
           throw new IllegalArgumentException(
                   "Cannot remove loan with active status, id=" + id);
        }
        boolean removed = loanRepository.removeById(id);

        if(!removed){
            throw new IllegalArgumentException("Loan not found, id=" + id);
        }
    }

    public List<Loan> getActiveLoans(){
        return loanRepository.findAllActive().stream()
                .map(dto -> LoanMapper.toEntity(
                        dto,
                        bookService.getById(dto.getBookId()),
                        userService.getById(dto.getUserId())
                ))
                .collect(Collectors.toList());
    }

    public List<Loan> getAllLoans(){
        return loanRepository.findAll().stream()
                .map(dto -> {
                    Book book = bookService.getByIdOrNull(dto.getBookId());
                    User user = userService.getByIdOrNull(dto.getUserId());

                    return LoanMapper.toEntity(dto, book, user);
                })
                .toList();
    }
}
