package com.mmf.financeflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "income")
@Getter
@Setter
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Income income = (Income) o;
        return Double.compare(amount, income.amount) == 0 && Objects.equals(dateTime, income.dateTime) && Objects.equals(description, income.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, dateTime, description);
    }
}