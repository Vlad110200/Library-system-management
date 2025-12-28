package com.vladl.library.repository;

import com.vladl.library.dto.LoanDto;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface LoanRepository{
    void save(LoanDto loan);
    Optional<LoanDto> findActiveByBookId(int bookId);
    boolean hasActiveLoan(Predicate<LoanDto> condition);
    boolean hasActiveLoanForBook(int bookId);
    boolean hasActiveLoanForUser(int userId);
    boolean hasActiveLoanForLoan(int loanId);
    List<LoanDto> findAll();
    boolean removeById(int loanId);
    List<LoanDto> findAllActive();
    void persist();
}
