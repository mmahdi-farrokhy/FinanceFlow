package com.mmf.financeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;
}
