package com.knoah.loantrackerapi.resources;

import com.knoah.loantrackerapi.domain.Account;
import com.knoah.loantrackerapi.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountResource {
    @Autowired
    AccountService accountService;

    // get all customer accounts
    @GetMapping("")
    public ResponseEntity<List<Account>> getAllAccounts(HttpServletRequest request) {
        int customerId = (Integer) request.getAttribute("customerId");
        List<Account> accounts = accountService.fetchAllAccounts(customerId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    // get a single customer account
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountById(HttpServletRequest request,
                                                    @PathVariable("accountId") Integer accountId) {
        int customerId = (Integer) request.getAttribute("customerId");
        Account account = accountService.fetchAccountById(customerId, accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    // create account for customer
    @PostMapping("")
    public ResponseEntity<Account> addAccount(HttpServletRequest request,
                                                @RequestBody Map<String, Object> accountMap) {
        int customerId = (Integer) request.getAttribute("customerId");
        Double openingBalance = Double.valueOf(accountMap.get("opening_balance").toString());
        Account account = accountService.addAccount(customerId, openingBalance);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    // update customer account
    @PutMapping("/{accountId}")
    public ResponseEntity<Map<String, Boolean>> updateAccount(HttpServletRequest request,
                                                               @PathVariable("accountId") Integer accountId,
                                                               @RequestBody Account account) {
        int customerId = (Integer) request.getAttribute("customerId");
        accountService.updateAccount(customerId, accountId, account);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    // delete customer account
    @DeleteMapping("/{accountId}")
    public ResponseEntity<Map<String, Boolean>> deleteAccount(HttpServletRequest request,
                                                               @PathVariable("accountId") Integer accountId) {
        int customerId = (Integer) request.getAttribute("customerId");
        accountService.removeAccountWithAllLoansAndPayments(customerId, accountId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
