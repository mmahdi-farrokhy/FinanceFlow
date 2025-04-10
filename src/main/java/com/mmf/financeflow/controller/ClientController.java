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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/income")
    public ResponseEntity<Income> createIncome(@RequestBody IncomeRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createIncome(request, username));
    }

    @PostMapping("/expense")
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createExpense(request, username));
    }

    @PostMapping("/budget")
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createBudget(request, username));
    }

    @PostMapping("/account")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createAccount(request, username));
    }

    @GetMapping("/income")
    public ResponseEntity<List<Income>> getIncomes() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getIncomes(username));
    }

    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> getExpenses() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getExpenses(username));
    }

    @GetMapping("/budgets")
    public ResponseEntity<List<Budget>> getBudgets() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getBudgets(username));
    }

    private static String getUsernameFromAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
