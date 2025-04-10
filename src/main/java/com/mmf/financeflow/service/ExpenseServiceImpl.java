package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.ExpenseRequest;
import com.mmf.financeflow.entity.Account;
import com.mmf.financeflow.entity.BudgetCategory;
import com.mmf.financeflow.entity.Client;
import com.mmf.financeflow.entity.Expense;
import com.mmf.financeflow.exception.InsufficientBalanceException;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.exception.MismatchedCategoryException;
import com.mmf.financeflow.repository.ExpenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {
    private ClientService clientService;
    private ExpenseRepository expenseRepository;

    @Override
    public Expense createExpense(ExpenseRequest request, String username) {
        Expense expense = new Expense(request.getAmount(), request.getDescription(), request.getCategory());
        double expenseAmount = expense.getAmount();
        BudgetCategory expenseCategory = expense.getCategory();

        Client client = clientService.findClientByUsername(username);

        if (expenseAmount <= 0) {
            throw new InvalidAmountException("Expense amount should be greater than 0!");
        }

        Account account = client.findAccountWithCategory(expenseCategory)
                .orElseThrow(() -> new MismatchedCategoryException("Account with category " + expenseCategory + " does not exist"));

        double accountBalance = account.getBalance();

        if (accountBalance < expenseAmount) {
            throw new InsufficientBalanceException("Expense amount " + expenseAmount + " is more than balance of " + expenseCategory + " account: " + accountBalance);
        }

        account.decreaseBalance(expenseAmount);

        client.addExpense(expense);
        clientService.save(client);
        return expense;
    }

    @Override
    public List<Expense> getExpenses(String username) {
        return expenseRepository.findExpensesByUsername(username);
    }

    @Override
    public List<Expense> getExpensesByCategory(String username, BudgetCategory category) {
        return expenseRepository.findExpensesByUsernameAndCategory(username, category);
    }
}
