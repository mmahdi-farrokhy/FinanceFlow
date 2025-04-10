package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.IncomeRequest;
import com.mmf.financeflow.entity.Income;
import com.mmf.financeflow.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@AllArgsConstructor
public class IncomeController {
    private ClientService clientService;

    @PostMapping("")
    public ResponseEntity<Income> createIncome(@RequestBody IncomeRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.createIncome(request, username));
    }

    @GetMapping("")
    public ResponseEntity<List<Income>> getIncomes() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(clientService.getIncomes(username));
    }

    private static String getUsernameFromAuthenticationContext() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
