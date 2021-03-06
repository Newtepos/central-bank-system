package com.example.cbts.repository;

import com.example.cbts.entites.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
     Optional<Currency> findByCurrency(String currency);
}
