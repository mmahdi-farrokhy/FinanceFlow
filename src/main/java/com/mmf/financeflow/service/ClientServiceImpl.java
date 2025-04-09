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

import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private ClientRepository clientRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public Client registerClient(RegisterRequest registerRequest) {
        String username = registerRequest.getUsername();
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
        Client registeredClient = new Client(username, encodedPassword);
        return clientRepository.save(registeredClient);
    }

    @Override
    public boolean existsByUsername(String username) {
        return clientRepository.existsByUsername(username);
    }

    @Override
    public Income createIncome(IncomeRequest request, String username) {
        Income income = new Income(request.getAmount(), request.getDescription());
        Client client = findClientByUsername(username);

        if (income.getAmount() <= 0) {
            throw new InvalidAmountException("Income amount should be greater than 0!");
        }

        client.increaseUnallocatedBudget(income.getAmount());
        client.addIncome(income);
        clientRepository.save(client);
        return income;
    }

    @Override
    public Expense createExpense(ExpenseRequest request, String username) {
        Expense expense = new Expense(request.getAmount(), request.getDescription(), request.getCategory());
        Client client = findClientByUsername(username);
        BudgetCategory newExpenseCategory = expense.getCategory();

        double expenseAmount = expense.getAmount();
        if (expenseAmount <= 0) {
            throw new InvalidAmountException("Expense amount should be greater than 0!");
        }

        Account account = findAccountWithCategory(client, newExpenseCategory)
                .orElseThrow(() -> new MismatchedCategoryException("Account with category " + newExpenseCategory + " does not exist"));

        double balance = account.getBalance();

        if (balance < expenseAmount) {
            throw new InsufficientBalanceException("Expense amount " + expenseAmount + " is more than balance of " + newExpenseCategory + " account: " + balance);
        }

        balance -= expenseAmount;
        account.setBalance(balance);

        client.addExpense(expense);
        clientRepository.save(client);
        return expense;
    }

    @Override
    public Budget createBudget(BudgetRequest request, String username) {
        Budget budget = new Budget(request.getAmount(), request.getCategory());
        double newBudgetAmount = budget.getAmount();
        BudgetCategory budgetCategory = budget.getCategory();

        Client client = findClientByUsername(username);
        double unallocatedBudget = client.getUnallocatedBudget();

        if (newBudgetAmount <= 0) {
            throw new InvalidAmountException("Budget amount should be greater than 0!");
        }

        if (newBudgetAmount > unallocatedBudget) {
            throw new InsufficientBalanceException("Budget amount " + newBudgetAmount + " is more than unallocated budget: " + unallocatedBudget);
        }


        Account account = findAccountWithCategory(client, budgetCategory)
                .orElseThrow(() -> new MismatchedCategoryException("Account with category " + budgetCategory + " does not exist"));

        account.increaseBalance(newBudgetAmount);
        client.decreaseUnallocatedBudget(newBudgetAmount);

        client.addBudget(budget);
        clientRepository.save(client);
        return budget;
    }

    @Override
    public Account createAccount(AccountRequest request, String username) {
        Account account = new Account(request.getTitle(), request.getCategory());
        BudgetCategory accountCategory = account.getCategory();

        Client client = findClientByUsername(username);

        findAccountWithCategory(client, account.getCategory())
                .ifPresent(acc -> {
                    throw new DuplicatedAccountCategoryException("Account from category " + accountCategory + " already exists!");
                });

        client.addAccount(account);
        clientRepository.save(client);
        return account;
    }

    private Client findClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private static Optional<Account> findAccountWithCategory(Client client, BudgetCategory newExpenseCategory) {
        return client.getAccounts().stream()
                .filter(account -> account.getCategory() == newExpenseCategory)
                .findFirst();
    }
}
