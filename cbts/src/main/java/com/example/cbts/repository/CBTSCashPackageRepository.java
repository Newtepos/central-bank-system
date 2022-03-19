package com.example.cbts.repository;

import com.example.cbts.entites.CBTSCashPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CBTSCashPackageRepository extends JpaRepository<CBTSCashPackage, Long> {
    Optional<CBTSCashPackage> findByPackageId(UUID packageID);
    CBTSCashPackage getByPackageId(UUID packageID);
}
