package com.example.cbts.repository;

import com.example.cbts.entites.Bank;
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
    void findByBankName() {

        Bank bank = new Bank();
        bank.setBankName("Testing");
        bankRepository.save(bank);

        Optional<Bank> result = bankRepository.findByBankName("Testing");

        assertTrue(result.isPresent());

    }
}