package com.mmf.financeflow.repository;

import com.mmf.financeflow.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);

    boolean exitsByUsername(String username);
}
