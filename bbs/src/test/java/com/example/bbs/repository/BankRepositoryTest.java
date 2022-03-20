package com.example.bbs.repository;

import com.example.bbs.entites.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankRepositoryTest {

    @Autowired
    BankRepository bankRepository;

    @Test
    void findByBankName_with_success() {
        Bank bank = new Bank();
        bank.setBankName("Testing");
        bankRepository.save(bank);

        Optional<Bank> result = bankRepository.findByBankName("Testing");

        assertTrue(result.isPresent());
    }

    @Test
    void findByCbtsKey_with_success() {
        Bank bank = new Bank();
        bank.setCbtsKey(99L);
        bankRepository.save(bank);

        Optional<Bank> result = bankRepository.findByCbtsKey(99L);

        assertTrue(result.isPresent());


    }

    @Test
    void getByCbtsKey_with_success() {
        Bank bank = new Bank();
        bank.setCbtsKey(99L);
        bankRepository.save(bank);

        Bank result = bankRepository.getByCbtsKey(99L);

        assertEquals(99L, result.getCbtsKey());
    }
}