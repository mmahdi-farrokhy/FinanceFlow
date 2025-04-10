package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.BudgetRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.Budget;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.exception.InsufficientBalanceException;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.exception.MismatchedCategoryException;
import com.mmf.financeflow.repository.BudgetRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {
    private ClientService clientService;
    private BudgetRepository budgetRepository;

    @Override

    public Budget createBudget(BudgetRequest request, String username) {
        Budget budget = new Budget(request.getAmount(), request.getCategory());
        double budgetAmount = budget.getAmount();
        BudgetCategory budgetCategory = budget.getCategory();

        Client client = clientService.findByUsername(username);
        double unallocatedBudget = client.getUnallocatedBudget();

        if (budgetAmount <= 0) {
            throw new InvalidAmountException("Budget amount should be greater than 0!");
        }

        if (budgetAmount > unallocatedBudget) {
            throw new InsufficientBalanceException("Budget amount " + budgetAmount + " is more than unallocated budget: " + unallocatedBudget);
        }

        Account account = client.findAccountWithCategory(budgetCategory)
                .orElseThrow(() -> new MismatchedCategoryException("Account with category " + budgetCategory + " does not exist"));

        account.increaseBalance(budgetAmount);
        client.decreaseUnallocatedBudget(budgetAmount);

        client.addBudget(budget);
        clientService.update(client);
        return budget;
    }

    @Override
    public List<Budget> getBudgets(String username) {
        return budgetRepository.findBudgetsByUsername(username);
    }

    @Override
    public List<Budget> getBudgetsByCategory(String username, BudgetCategory category) {
        return budgetRepository.findBudgetsByUsernameAndCategory(username, category);
    }
}
