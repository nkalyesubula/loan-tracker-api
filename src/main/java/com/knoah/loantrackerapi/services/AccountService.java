package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.Account;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;

import java.util.List;

public interface AccountService {
    List<Account> fetchAllAccounts(Integer userId);

    Account fetchAccountById(Integer customerId, Integer accountId) throws LtResourceNotFoundException;
    Account addAccount(Integer customerId, Double openingBalance) throws LtBadRequestException;
    void updateAccount(Integer customerId, Integer accountId, Account account) throws LtBadRequestException;
    void removeAccountWithAllLoansAndPayments(Integer customerId, Integer accountId) throws LtResourceNotFoundException;
}
