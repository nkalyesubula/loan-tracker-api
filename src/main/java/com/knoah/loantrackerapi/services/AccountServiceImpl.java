package com.knoah.loantrackerapi.services;

import com.knoah.loantrackerapi.domain.Account;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;
import com.knoah.loantrackerapi.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public List<Account> fetchAllAccounts(Integer userId) {
        return accountRepository.findAll(userId);
    }

    @Override
    public Account fetchAccountById(Integer customerId, Integer accountId) throws LtResourceNotFoundException {
        return accountRepository.findById(customerId, accountId);
    }

    @Override
    public Account addAccount(Integer customerId, Double openingBalance) throws LtBadRequestException {
        int accountId = accountRepository.create(customerId, openingBalance);
        return accountRepository.findById(customerId, accountId);
    }

    @Override
    public void updateAccount(Integer customerId, Integer accountId, Account account) throws LtBadRequestException {
        accountRepository.update(customerId, accountId, account);
    }

    @Override
    public void removeAccountWithAllLoansAndPayments(Integer customerId, Integer accountId) throws LtResourceNotFoundException {
        this.fetchAccountById(customerId, accountId);
        accountRepository.removeById(customerId, accountId);
    }
}
