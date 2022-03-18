package com.example.cbts.repository;

import com.example.cbts.entites.CashPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CashPackageRepository extends JpaRepository<CashPackage, UUID> {
}
