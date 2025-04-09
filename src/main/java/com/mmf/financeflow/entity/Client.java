package com.mmf.financeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
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

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
        this.unallocatedBudget = 0;
    }

    public void addAccount(Account newAccount) {
        if (accounts == null) {
            accounts = new LinkedList<>();
        }

        newAccount.setClient(this);
        accounts.add(newAccount);
    }

    public void addIncome(Income newIncome) {
        if (incomes == null) {
            incomes = new LinkedList<>();
        }

        newIncome.setClient(this);
        incomes.add(newIncome);
    }

    public void addExpense(Expense newExpense) {
        if (expenses == null) {
            expenses = new LinkedList<>();
        }

        newExpense.setClient(this);
        expenses.add(newExpense);
    }

    public void addBudget(Budget newBudget) {
        if (budgets == null) {
            budgets = new LinkedList<>();
        }

        newBudget.setClient(this);
        budgets.add(newBudget);
    }

    public void increaseUnallocatedBudget(double amount) {
        unallocatedBudget += amount;
    }

    public void decreaseUnallocatedBudget(double amount) {
        unallocatedBudget -= amount;
    }

    public Optional<Account> findAccountWithCategory(BudgetCategory newExpenseCategory) {
        return getAccounts().stream()
                .filter(account -> account.getCategory() == newExpenseCategory)
                .findFirst();
    }
}
