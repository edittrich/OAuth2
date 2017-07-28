package de.edittrich.oauth2.data.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomersRepository extends JpaRepository<Customers, Long> {
    Optional<Customers> findByCustomerId(String customerId);
}