package com.knoah.loantrackerapi.resources;

import com.knoah.loantrackerapi.domain.LoanPayment;
import com.knoah.loantrackerapi.services.LoanPaymentService;
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
@RequestMapping("/api/v1/loans/{loanId}/payments")
@SecurityRequirement(name = "Bearer Authentication")
public class LoanPaymentResource {
    @Autowired
    LoanPaymentService loanPaymentService;

    // make a loan payment
    @PostMapping("")
    @Operation(summary = "Make a loan payment")
    public ResponseEntity<LoanPayment> addLoanPayment(HttpServletRequest request,
                                                      @PathVariable("loanId") Integer loanId,
                                                     @Schema(implementation=LoanPayment.class) @RequestBody Map<String, Object> transactionMap) {
        int customerId = (Integer) request.getAttribute("customerId");
        Double amount = Double.valueOf(transactionMap.get("amount").toString());
        String note = (String) transactionMap.get("note");
        LoanPayment loanPayment = loanPaymentService.addLoanPayment(customerId,loanId,amount, note);
        return new ResponseEntity<>(loanPayment, HttpStatus.CREATED);
    }

    // get a loan payment id
    @GetMapping("/{loanPaymentId}")
    @Operation(summary = "Get Loan payment details by Id")
    public ResponseEntity<LoanPayment> getLoanPaymentById(HttpServletRequest request,
                                                          @PathVariable("loanId") Integer loanId,
                                                          @PathVariable("loanPaymentId") Integer loanPaymentId) {
        int userId = (Integer) request.getAttribute("userId");
        LoanPayment loanPayment = loanPaymentService.fetchLoanPaymentById(userId, loanId, loanPaymentId);
        return new ResponseEntity<>(loanPayment, HttpStatus.OK);

    }

    // fetch all payments made for a particular loan
    @GetMapping("")
    @Operation(summary = "Get all loan payment history")
    public ResponseEntity<List<LoanPayment>> getAllLoanPayments(HttpServletRequest request,
                                                                @PathVariable("loanId") Integer loanId) {
        int customerId = (Integer) request.getAttribute("customerId");
        List<LoanPayment> transactions = loanPaymentService.fetchAllLoanPayments(customerId, loanId);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Map> handleNumberFormatException(NumberFormatException ex) {
        Map<String, String> map = new HashMap<>();
        map.put("error", "Bad Request");
        map.put("message", "Invalid loan id");
        return ResponseEntity.badRequest().body(map);
    }
}
