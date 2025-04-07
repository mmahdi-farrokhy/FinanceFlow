package com.mmf.financeflow.controller;

import com.mmf.financeflow.entity.Income;
import com.mmf.financeflow.repository.IncomeRepository;
import com.mmf.financeflow.service.IncomeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/income")
@AllArgsConstructor
public class IncomeController {
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> createIncome(@RequestBody Income income) {
        return ResponseEntity.ok(incomeService.create(income));
    }

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        return ResponseEntity.ok(incomeService.findAll());
    }
}
