package com.example.bbs.repository;

import com.example.bbs.entites.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CurrencyRepositoryTest {

    @Autowired
    CurrencyRepository currencyRepository;

    @Test
    void findByCurrency_with_success() {

        Currency currency = new Currency();
        currency.setCurrency("Testing");
        currencyRepository.save(currency);

        Optional<Currency> result = currencyRepository.findByCurrency("Testing");

        assertTrue(result.isPresent());
    }
}