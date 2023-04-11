package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.LoanPayment;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;

import java.util.Date;
import java.util.List;

public interface LoanPaymentService {
    List<LoanPayment> fetchAllLoanPayments(Integer customerId, Integer loanId);
    LoanPayment fetchLoanPaymentById(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException;
    LoanPayment addLoanPayment(Integer customerId, Integer loanId, Double amount, String note) throws LtBadRequestException;
    void updateLoanPayment(Integer customerId, Integer loanId, Integer loanPaymentId, LoanPayment loanPayment) throws LtBadRequestException;
    void removeLoanPayment(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException;
}
