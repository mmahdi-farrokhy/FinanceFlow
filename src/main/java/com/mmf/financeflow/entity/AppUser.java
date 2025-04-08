package com.mmf.financeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

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
    private Set<Account> accounts;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Income> incomes;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private List<Budget> budgets;
}
