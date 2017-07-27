package de.edittrich.oauth2.data.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodesRepository extends JpaRepository<Codes, Long> {
    Optional<Codes> findByCode(String code);
}