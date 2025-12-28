package com.vladl.library.repository.json;

import com.vladl.library.dto.LoanDto;
import com.vladl.library.model.Loan;
import com.vladl.library.model.LoanStatus;
import com.vladl.library.repository.LoanRepository;
import com.vladl.library.storage.JsonStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class JsonLoanRepository implements LoanRepository {

    private final List<LoanDto> loans;
    private final static String FILE = "loans.json";

    public JsonLoanRepository(){
        this.loans = new ArrayList<>(
                JsonStorage.load(FILE, LoanDto.class)
        );
        int maxId = loans.stream()
                .mapToInt(LoanDto::getId)
                .max()
                .orElse(0);
        Loan.setCounter(maxId + 1);
    }

    @Override
    public void save(LoanDto loan){
        loans.add(loan);
        persist();
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
        boolean removed = loans.removeIf(dto -> dto.getId() == loanId);
        if(removed){
            persist();
        }
        return removed;
    }

    @Override
    public List<LoanDto> findAllActive(){
        return loans.stream().filter(dto ->
                dto.getStatus() == LoanStatus.ACTIVE)
                .toList();
    }

    @Override
    public void persist(){
        JsonStorage.save(FILE, loans);
    }
}
