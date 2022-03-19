package com.example.bbs.repository;

import com.example.bbs.entites.CBTSCashPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CBTSCashPackageRepository extends JpaRepository<CBTSCashPackage, Long> {
    Optional<CBTSCashPackage> findByPackageId(UUID packageID);
    CBTSCashPackage getByPackageId(UUID packageID);
}
