package com.example.bbs.repository;

import com.example.bbs.entites.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
     Optional<Currency> findByCurrency(String currency);
}
