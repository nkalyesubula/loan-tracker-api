package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.Loan;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;

import java.util.Date;
import java.util.List;

public interface LoanService {
    List<Loan> fetchAllLoans(Integer customerId);
    Loan fetchLoanById(Integer customerId, Integer loanId) throws LtResourceNotFoundException;
    Loan addLoan(Integer customerId, Double loanAmount) throws LtBadRequestException;
    void updateLoan(Integer customerId, Integer loanId, Loan loan) throws LtBadRequestException;
    void removeLoan(Integer customerId, Integer loanId) throws LtResourceNotFoundException;
}