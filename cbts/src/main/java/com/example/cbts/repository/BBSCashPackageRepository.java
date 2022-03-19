package com.example.cbts.repository;

import com.example.cbts.entites.BBSCashPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BBSCashPackageRepository extends JpaRepository<BBSCashPackage, Long> {
    Optional<BBSCashPackage> findByPackageId(UUID packageID);
    BBSCashPackage getByPackageId(UUID packageId);
}
