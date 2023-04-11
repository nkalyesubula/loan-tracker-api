package com.knoah.loantrackerapi.domain;

import java.util.Date;

public class Loan {
    private Integer loanId;
    private Integer customerId;
    private Double loanAmount;
    private Double outstandingBalance;
    private String disbursementDate;

    public Loan(Integer loanId, Integer customerId, Double loanAmount, Double outstandingBalance, String disbursementDate) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.loanAmount = loanAmount;
        this.outstandingBalance = outstandingBalance;
        this.disbursementDate = disbursementDate;
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

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(Double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }
}