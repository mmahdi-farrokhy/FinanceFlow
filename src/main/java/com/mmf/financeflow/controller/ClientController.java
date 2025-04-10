package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.*;
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

    @PostMapping("/budget")
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createBudget(request, username));
    }

    @GetMapping("/budget")
    public ResponseEntity<List<Budget>> getBudgets() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getBudgets(username));
    }

    @GetMapping("/budget/category")
    public ResponseEntity<List<Budget>> getBudgetsByCategory(@RequestParam BudgetCategory category){
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getBudgetsByCategory(username, category));
    }

    private static String getUsernameFromAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
