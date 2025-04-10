package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.ExpenseRequest;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Expense;
import com.mmf.financeflow.service.ClientService;
import com.mmf.financeflow.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mmf.financeflow.util.SecurityUtil.getUsernameFromAuthenticationContext;

@RestController
@RequestMapping("/api/expense")
@AllArgsConstructor
public class ExpenseController {
    private ClientService clientService;

    @PostMapping("")
    public ResponseEntity<Expense> createExpense(@RequestBody ExpenseRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createExpense(request, username));
    }

    @GetMapping("")
    public ResponseEntity<List<Expense>> getExpenses() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getExpenses(username));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@RequestParam BudgetCategory category) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getExpensesByCategory(username, category));
    }
}
