package com.mmf.financeflow.service;

import com.mmf.financeflow.dto.*;
import com.mmf.financeflow.entity.*;
import com.mmf.financeflow.exception.DuplicatedAccountCategoryException;
import com.mmf.financeflow.exception.InsufficientBalanceException;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.exception.MismatchedCategoryException;
import com.mmf.financeflow.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void registerClient(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Client registeredClient = new Client(username, encodedPassword);
        clientRepository.save(registeredClient);
    }

    @Override
    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public Income createIncome(IncomeRequest request, String username) {
        Income income = new Income(request.getAmount(), request.getDescription());
        Client client = findClientByUsername(username);

        double incomeAmount = income.getAmount();
        if (incomeAmount <= 0) {
            throw new InvalidAmountException("Income amount should be greater than 0!");
        }

        client.increaseUnallocatedBudget(incomeAmount);
        client.addIncome(income);
        clientRepository.save(client);
        return income;
    }

    @Override
    public Expense createExpense(ExpenseRequest request, String username) {
        Expense expense = new Expense(request.getAmount(), request.getDescription(), request.getCategory());
        double expenseAmount = expense.getAmount();
        BudgetCategory expenseCategory = expense.getCategory();

        Client client = findClientByUsername(username);

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
        clientRepository.save(client);
        return expense;
    }

    @Override
    public Budget createBudget(BudgetRequest request, String username) {
        Budget budget = new Budget(request.getAmount(), request.getCategory());
        double budgetAmount = budget.getAmount();
        BudgetCategory budgetCategory = budget.getCategory();

        Client client = findClientByUsername(username);
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
        clientRepository.save(client);
        return budget;
    }

    @Override
    public Account createAccount(AccountRequest request, String username) {
        Account account = new Account(request.getTitle(), request.getCategory());
        BudgetCategory accountCategory = account.getCategory();

        Client client = findClientByUsername(username);

        client.findAccountWithCategory(account.getCategory())
                .ifPresent(acc -> {
                    throw new DuplicatedAccountCategoryException("Account from category " + accountCategory + " already exists!");
                });

        client.addAccount(account);
        clientRepository.save(client);
        return account;
    }

    @Override
    public List<Income> getIncomes(String username) {
        return clientRepository.findIncomesByUsername(username);
    }

    @Override
    public List<Expense> getExpenses(String username) {
        return clientRepository.findExpensesByUsername(username);
    }

    @Override
    public List<Budget> getBudgets(String username) {
        return clientRepository.findBudgetsByUsername(username);
    }

    private Client findClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }
}
