package com.knoah.loantrackerapi.domain;

import java.util.Date;

public class LoanPayment {
    private Integer loanPaymentId;
    private Integer loanId;
    private Integer customerId;
    private Double amount;
    private String note;
    private String paymentDate;

    public LoanPayment(Integer loanPaymentId, Integer loanId, Integer customerId, Double amount, String note, String paymentDate) {
        this.loanPaymentId = loanPaymentId;
        this.loanId = loanId;
        this.customerId = customerId;
        this.amount = amount;
        this.note = note;
        this.paymentDate = paymentDate;
    }

    public Integer getLoanPaymentId() {
        return loanPaymentId;
    }

    public void setLoanPaymentId(Integer loanPaymentId) {
        this.loanPaymentId = loanPaymentId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
}