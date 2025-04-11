package com.mmf.financeflow.controller;

import com.mmf.financeflow.dto.IncomeRequest;
import com.mmf.financeflow.entity.Income;
import com.mmf.financeflow.service.IncomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mmf.financeflow.util.SecurityUtil.getUsernameFromAuthenticationContext;

@RestController
@RequestMapping("/api/income")
@AllArgsConstructor
public class IncomeController {
    private IncomeService incomeService;

    @PostMapping("")
    public ResponseEntity<Income> createIncome(@RequestBody IncomeRequest request) {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(incomeService.create(request, username));
    }

    @GetMapping("")
    public ResponseEntity<List<Income>> getIncomes() {
        String username = getUsernameFromAuthenticationContext();
        return ResponseEntity.ok(incomeService.findAll(username));
    }
}
