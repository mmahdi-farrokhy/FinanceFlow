package com.mmf.financeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private BudgetCategory category;

    @Column(name = "balance", nullable = false)
    private double balance;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Account(String title, BudgetCategory category) {
        this.title = title;
        this.category = category;
        this.balance = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(balance, account.balance) == 0 && Objects.equals(title, account.title) && category == account.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, category, balance);
    }
}
