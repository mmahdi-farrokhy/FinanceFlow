package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.AccountRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {
    private ClientService clientService;

    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createAccount(request, username));
    }

    @GetMapping("")
    public ResponseEntity<List<Account>> getAccounts() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getAccounts(username));
    }

    @GetMapping("/category")
    public ResponseEntity<Account> getAccountByType(@RequestParam BudgetCategory category){
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getAccountByType(username, category));
    }

    private static String getUsernameFromAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
