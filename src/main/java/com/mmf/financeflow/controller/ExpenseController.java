package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.ExpenseRequest;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Expense;
import com.mmf.financeflow.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mmf.financeflow.util.SecurityUtil.getUsernameFromAuthenticationContext;

@RestController
@RequestMapping("/api/expense")
@AllArgsConstructor
public class ExpenseController {
    private ExpenseService expenseService;

    @PostMapping("")
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(expenseService.create(request, username));
    }

    @GetMapping("")
    public ResponseEntity<List<Expense>> getExpenses() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(expenseService.findAll(username));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@RequestParam BudgetCategory category) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(expenseService.findByCategory(username, category));
    }
}
