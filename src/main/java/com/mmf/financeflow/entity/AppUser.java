package com.mmf.financeflow.entity;

import com.mmf.financeflow.exception.DuplicatedAccountCategoryException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "app_user")
@Getter
@Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "unallocated_budget")
    private double unallocatedBudget;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Account> accounts;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Income> incomes;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Budget> budgets;

    public void addAccount(Account newAccount) {
        if (accounts == null) {
            accounts = new LinkedList<>();
        }

        List<BudgetCategory> accountCategories = accounts.stream()
                .map(Account::getCategory)
                .toList();

        BudgetCategory newAccountCategory = newAccount.getCategory();
        if (accountCategories.contains(newAccountCategory)) {
            throw new DuplicatedAccountCategoryException("Account from category " + newAccountCategory + " already exists!");
        } else {
            accounts.add(newAccount);
        }
    }
}
