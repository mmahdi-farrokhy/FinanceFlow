package com.mmf.financeflow.entity;

import com.mmf.financeflow.exception.DuplicatedAccountCategoryException;
import com.mmf.financeflow.exception.InsufficientBalanceException;
import com.mmf.financeflow.exception.InvalidAmountException;
import com.mmf.financeflow.exception.MismatchedCategoryException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "unallocated_budget")
    private double unallocatedBudget;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Income> incomes;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Budget> budgets;

    public void addAccount(Account newAccount) {
        if (accounts == null) {
            accounts = new LinkedList<>();
        }

        List<BudgetCategory> accountCategories =
                accounts.stream()
                        .map(Account::getCategory)
                        .toList();

        BudgetCategory newAccountCategory = newAccount.getCategory();
        if (accountCategories.contains(newAccountCategory)) {
            throw new DuplicatedAccountCategoryException("Account from category " + newAccountCategory + " already exists!");
        } else {
            newAccount.setClient(this);
            accounts.add(newAccount);
        }
    }

    public void addIncome(Income newIncome) {
        if (incomes == null) {
            incomes = new LinkedList<>();
        }

        if (newIncome.getAmount() <= 0) {
            throw new InvalidAmountException("Income amount should be greater than 0!");
        }

        unallocatedBudget += newIncome.getAmount();

        newIncome.setClient(this);
        incomes.add(newIncome);
    }

    public void addExpense(Expense newExpense) {
        if (expenses == null) {
            expenses = new LinkedList<>();
        }

        double newExpenseAmount = newExpense.getAmount();
        if (newExpenseAmount <= 0) {
            throw new InvalidAmountException("Expense amount should be greater than 0!");
        }

        BudgetCategory newExpenseCategory = newExpense.getCategory();
        Optional<Account> accountWithSameCategory = accounts.stream()
                .filter(account -> account.getCategory() == newExpenseCategory)
                .findFirst();

        if (accountWithSameCategory.isEmpty()) {
            throw new MismatchedCategoryException("Account with category " + newExpenseCategory + " does not exist");
        }

        Account account = accountWithSameCategory.get();
        double balance = account.getBalance();

        if (balance < newExpenseAmount) {
            throw new InsufficientBalanceException("Expense amount " + newExpenseAmount + " is more than balance of " + newExpenseCategory + " account: " + balance);
        }

        balance -= newExpenseAmount;
        account.setBalance(balance);

        newExpense.setClient(this);
        expenses.add(newExpense);
    }

    public void addBudget(Budget newBudget) {
        if (budgets == null) {
            budgets = new LinkedList<>();
        }

        double newBudgetAmount = newBudget.getAmount();
        if (newBudgetAmount <= 0) {
            throw new InvalidAmountException("Budget amount should be greater than 0!");
        }

        if (newBudgetAmount > unallocatedBudget) {
            throw new InsufficientBalanceException("Budget amount " + newBudgetAmount + " is more than unallocated budget: " + unallocatedBudget);
        }

        BudgetCategory newBudgetCategory = newBudget.getCategory();
        Optional<Account> accountWithSameCategory = accounts.stream()
                .filter(account -> account.getCategory() == newBudgetCategory)
                .findFirst();

        if (accountWithSameCategory.isEmpty()) {
            throw new MismatchedCategoryException("Account with category " + newBudgetCategory + " does not exist");
        }

        Account account = accountWithSameCategory.get();
        double balance = account.getBalance();
        balance += newBudgetAmount;
        account.setBalance(balance);

        unallocatedBudget -= newBudgetAmount;

        newBudget.setClient(this);
        budgets.add(newBudget);
    }
}
