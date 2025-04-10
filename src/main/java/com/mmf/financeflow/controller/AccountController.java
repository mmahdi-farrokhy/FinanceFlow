package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.AccountRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mmf.financeflow.util.SecurityUtil.getUsernameFromAuthenticationContext;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;

    @PostMapping("")
    public ResponseEntity<Account> createAccount(@RequestBody AccountRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(accountService.createAccount(request, username));
    }

    @GetMapping("")
    public ResponseEntity<List<Account>> getAccounts() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(accountService.getAccounts(username));
    }

    @GetMapping("/category")
    public ResponseEntity<Account> getAccountByType(@RequestParam BudgetCategory category) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(accountService.getAccountByType(username, category));
    }
}
