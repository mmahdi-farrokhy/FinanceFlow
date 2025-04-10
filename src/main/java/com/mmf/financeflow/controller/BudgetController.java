package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.BudgetRequest;
import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.service.BudgetServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mmf.financeflow.util.SecurityUtil.getUsernameFromAuthenticationContext;

@RestController
@RequestMapping("/api/budget")
@AllArgsConstructor
public class BudgetController {
    private BudgetServiceImpl budgetService;

    @PostMapping("")
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(budgetService.createBudget(request, username));
    }

    @GetMapping("")
    public ResponseEntity<List<Budget>> getBudgets() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(budgetService.getBudgets(username));
    }

    @GetMapping("/category")
    public ResponseEntity<List<Budget>> getBudgetsByCategory(@RequestParam BudgetCategory category) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(budgetService.getBudgetsByCategory(username, category));
    }
}
