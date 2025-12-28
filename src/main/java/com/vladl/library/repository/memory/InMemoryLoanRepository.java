package com.vladl.library.repository.memory;

import com.vladl.library.dto.LoanDto;
import com.vladl.library.model.LoanStatus;
import com.vladl.library.repository.LoanRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class InMemoryLoanRepository implements LoanRepository {

    private final List<LoanDto> loans = new ArrayList<>();

    @Override
    public void save(LoanDto loan){
        loans.add(loan);
    }

    @Override
    public Optional<LoanDto> findActiveByBookId(int bookId){
        return loans.stream()
                .filter(dto -> dto.getBookId() == bookId &&
                        dto.getStatus() == LoanStatus.ACTIVE)
                .findFirst();
    }

    @Override
    public boolean hasActiveLoan(Predicate<LoanDto> condition){
        return loans.stream()
                .anyMatch(dto ->
                        dto.getStatus() == LoanStatus.ACTIVE && condition.test(dto));
    }

    @Override
    public boolean hasActiveLoanForBook(int bookId){
        return hasActiveLoan(dto -> dto.getBookId() == bookId);
    }

    @Override
    public boolean hasActiveLoanForUser(int userId){
        return hasActiveLoan(dto -> dto.getUserId() == userId);
    }

    @Override
    public boolean hasActiveLoanForLoan(int loanId){
        return hasActiveLoan(dto -> dto.getId() == loanId);
    }

    @Override
    public List<LoanDto> findAll(){
        return new ArrayList<>(loans);
    }

    @Override
    public boolean removeById(int loanId){
        return loans.removeIf(dto -> dto.getId() == loanId);
    }

    @Override
    public List<LoanDto> findAllActive(){
        return loans.stream()
                .filter(dto ->
                        dto.getStatus() == LoanStatus.ACTIVE)
                .toList();
    }

    @Override
    public void persist(){

    }
}
