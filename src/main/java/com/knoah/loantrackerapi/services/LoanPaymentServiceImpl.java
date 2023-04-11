package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.LoanPayment;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;
import com.knoah.loantrackerapi.repositories.LoanPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class LoanPaymentServiceImpl implements LoanPaymentService{
    @Autowired
    LoanPaymentRepository loanPaymentRepository;
    @Override
    public List<LoanPayment> fetchAllLoanPayments(Integer customerId, Integer loanId) {
        return loanPaymentRepository.findAll(customerId, loanId);
    }

    @Override
    public LoanPayment fetchLoanPaymentById(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException {
        return loanPaymentRepository.findById(customerId,loanId,loanPaymentId);
    }

    @Override
    public LoanPayment addLoanPayment(Integer customerId, Integer loanId, Double amount, String note) throws LtBadRequestException {
        int loanPaymentId = loanPaymentRepository.create(customerId, loanId, amount, note);
        return  loanPaymentRepository.findById(customerId, loanId, loanPaymentId);
    }

    @Override
    public void updateLoanPayment(Integer customerId, Integer loanId, Integer loanPaymentId, LoanPayment loanPayment) throws LtBadRequestException {

    }

    @Override
    public void removeLoanPayment(Integer customerId, Integer loanId, Integer loanPaymentId) throws LtResourceNotFoundException {

    }
}
