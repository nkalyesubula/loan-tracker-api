package com.knoah.loantrackerapi.domain;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class Loan {
    @Schema(hidden=true)
    private Integer loanId;
    @Schema(hidden=true)
    private Integer customerId;
    private Double loanAmount;
    @Schema(hidden=true)
    private Double outstandingBalance;
    @Schema(hidden=true)
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