package com.example.bbs.repository;

import com.example.bbs.entites.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByBankName(String bankName);
    Optional<Bank> findByCbtsKey(Long key);
}
