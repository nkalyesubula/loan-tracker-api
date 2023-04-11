package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.Loan;
import com.knoah.loantrackerapi.domain.LoanPayment;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;

import java.util.List;

public interface LoanPaymentRepository {
    List<LoanPayment> findAll(Integer customerId, Integer loanId);
    LoanPayment findById(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException;
    Integer create(Integer customerId, Integer loanId, Double amount, String note) throws LtResourceNotFoundException;
    void update(Integer customerId, Integer loanId, Integer loanPaymentId, LoanPayment loanPayment) throws LtResourceNotFoundException;
    void removeById(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException;
}
