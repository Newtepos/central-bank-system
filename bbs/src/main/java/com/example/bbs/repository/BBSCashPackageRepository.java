package com.example.bbs.repository;

import com.example.bbs.entites.BBSCashPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BBSCashPackageRepository extends JpaRepository<BBSCashPackage, Long> {
    Optional<BBSCashPackage> findByPackageId(UUID packageID);
    BBSCashPackage getByPackageId(UUID packageId);
}
