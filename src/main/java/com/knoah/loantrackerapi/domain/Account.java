package com.knoah.loantrackerapi.domain;

public class Account {
    private Integer accountId;
    private Integer customerId;
    private Double openingBalance;

    public Account(Integer accountId, Integer customerId, Double openingBalance) {
        this.accountId = accountId;
        this.customerId = customerId;
        this.openingBalance = openingBalance;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }
}
