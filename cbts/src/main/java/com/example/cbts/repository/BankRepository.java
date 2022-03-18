package com.example.cbts.repository;

import com.example.cbts.entites.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
