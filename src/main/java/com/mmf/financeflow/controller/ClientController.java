package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.Expense;
import com.mmf.financeflow.entity.Income;
import com.mmf.financeflow.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (clientService.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken.");
        }

        clientService.registerClient(request);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/create-income")
    public ResponseEntity<Income> createIncome(@RequestBody IncomeRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createIncome(request, username));
    }

    @PostMapping("/create-expense")
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createExpense(request, username));
    }

    @PostMapping("/create-budget")
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest request) {
        return ResponseEntity.ok(clientService.createBudget(request));
    }

    @PostMapping("/create-account")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        return ResponseEntity.ok(clientService.createAccount(request));
    }

    private static String getUsernameFromAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
