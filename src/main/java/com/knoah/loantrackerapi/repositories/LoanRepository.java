package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.Loan;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;

import java.util.List;

public interface LoanRepository {
    List<Loan> findAll(Integer customerId);
    Loan findById(Integer customerId, Integer loanId) throws LtResourceNotFoundException;
    Integer create(Integer customerId, Double loanAmount) throws LtResourceNotFoundException;
    void update(Integer customerId, Integer loanId, Loan loan) throws LtResourceNotFoundException;
    void removeById(Integer customerId, Integer loanId) throws LtResourceNotFoundException;
}
