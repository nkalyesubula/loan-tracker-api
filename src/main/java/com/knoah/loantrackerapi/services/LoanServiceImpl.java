package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.Loan;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;
import com.knoah.loantrackerapi.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class LoanServiceImpl implements LoanService {
    @Autowired
    LoanRepository loanRepository;
    @Override
    public List<Loan> fetchAllLoans(Integer customerId) {
        return loanRepository.findAll(customerId);
    }

    @Override
    public Loan fetchLoanById(Integer customerId, Integer loanId) throws LtResourceNotFoundException {
        return null;
    }

    @Override
    public Loan addLoan(Integer customerId, Double loanAmount) throws LtBadRequestException {
        int loanId = loanRepository.create(customerId, loanAmount);
        return loanRepository.findById(customerId, loanId);
    }

    @Override
    public void updateLoan(Integer customerId, Integer loanId, Loan loan) throws LtBadRequestException {

    }

    @Override
    public void removeLoan(Integer customerId, Integer loanId) throws LtResourceNotFoundException {

    }
}
