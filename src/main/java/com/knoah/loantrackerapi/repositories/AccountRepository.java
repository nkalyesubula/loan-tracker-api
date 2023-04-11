package com.knoah.loantrackerapi.repositories;

import com.knoah.loantrackerapi.domain.Account;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll(Integer customerId) throws LtResourceNotFoundException;
    Account findById(Integer customerId, Integer accountId) throws LtResourceNotFoundException;
    Integer create(Integer customerId, Double openingBalance) throws LtBadRequestException;
    void update(Integer customerId, Integer accountId, Account account) throws LtBadRequestException;
    void removeById(Integer customerId, Integer accountId);
}
