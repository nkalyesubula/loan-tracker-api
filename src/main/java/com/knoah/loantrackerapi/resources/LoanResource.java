package com.knoah.loantrackerapi.resources;

import com.knoah.loantrackerapi.domain.Account;
import com.knoah.loantrackerapi.domain.ApiMetrics;
import com.knoah.loantrackerapi.domain.Loan;
import com.knoah.loantrackerapi.exceptions.LtBadRequestException;
import com.knoah.loantrackerapi.exceptions.LtResourceNotFoundException;
import com.knoah.loantrackerapi.services.AccountService;
import com.knoah.loantrackerapi.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/loans")
@SecurityRequirement(name = "Bearer Authentication")
public class LoanResource {
    private final ApiMetrics metrics = new ApiMetrics();
    @Autowired
    LoanService loanService;

    @Autowired
    AccountService accountService;

    // add a loan
    @PostMapping("")
    @Operation(summary = "Create new Loan Details")
    public ResponseEntity<Loan> addLoan(HttpServletRequest request,
                                        @Schema(implementation= Loan.class)  @RequestBody Map<String, Object> loanMap) {
        metrics.incrementTotalRequests();
        int customerId = (Integer) request.getAttribute("customerId");
        Double loanAmount = Double.valueOf(loanMap.get("loanAmount").toString());
        Loan loan = loanService.addLoan(customerId, loanAmount);
        return new ResponseEntity<>(loan, HttpStatus.CREATED);
    }

    // get loan status by account number
    @GetMapping("/{accountId}")
    @Operation(summary = "Get Loan Status By Account Number" )
    public ResponseEntity<List<Loan>> getLoanByAccountId(HttpServletRequest request,
                                                      @PathVariable("accountId") Integer accountId) {
        metrics.incrementTotalRequests();
        // check if account number is a 10 digit number
        if(!accountId.toString().matches("\\d{10}")) {
          //  metrics.incrementFailedValidations();
            throw new LtBadRequestException("Account number must be a 10 digit number");
        }
        // check if account number exists
        int customerId = (Integer) request.getAttribute("customerId");
        Account account = accountService.fetchAccountById(customerId, accountId);
        if(account == null)
            //metrics.incrementNegativeRequests();
            throw new LtResourceNotFoundException("Account number not found");

        // fetch all loans related to the customer
        List<Loan> loans = loanService.fetchAllLoans(customerId);
        if(loans.isEmpty())
           // metrics.incrementNegativeRequests();
            throw new LtResourceNotFoundException("No loan found");
        // metrics.incrementPositiveRequests();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map> handleNumberFormatException(NumberFormatException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Bad Request");
        map.put("message", "Account number must be a 10 digit number");
        return ResponseEntity.badRequest().body(map);
    }
}
