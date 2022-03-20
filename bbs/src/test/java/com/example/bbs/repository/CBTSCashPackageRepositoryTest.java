package com.example.bbs.repository;

import com.example.bbs.entites.CBTSCashPackage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CBTSCashPackageRepositoryTest {

    @Autowired
    CBTSCashPackageRepository cbtsCashPackageRepository;

    @Test
    void findByPackageId_with_success() {
        CBTSCashPackage cashPackage = new CBTSCashPackage();
        UUID uuid = UUID.randomUUID();
        cashPackage.setPackageID(uuid);
        cbtsCashPackageRepository.save(cashPackage);

        Optional<CBTSCashPackage> result = cbtsCashPackageRepository.findByPackageId(uuid);

        assertTrue(result.isPresent());
    }

    @Test
    void getByPackageId_with_success() {
        CBTSCashPackage cashPackage = new CBTSCashPackage();
        UUID uuid = UUID.randomUUID();
        cashPackage.setPackageID(uuid);
        cbtsCashPackageRepository.save(cashPackage);

        CBTSCashPackage result = cbtsCashPackageRepository.getByPackageId(uuid);

        assertEquals(uuid, result.getPackageID());
    }
}