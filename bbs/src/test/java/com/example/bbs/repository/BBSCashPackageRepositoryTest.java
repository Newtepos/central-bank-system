package com.example.bbs.repository;

import com.example.bbs.entites.BBSCashPackage;
import com.example.bbs.entites.CBTSCashPackage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BBSCashPackageRepositoryTest {

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    @Test
    void findByPackageId_with_success() {
        BBSCashPackage cashPackage = new BBSCashPackage();
        UUID uuid = UUID.randomUUID();
        cashPackage.setPackageId(uuid);
        bbsCashPackageRepository.save(cashPackage);

        Optional<BBSCashPackage> result = bbsCashPackageRepository.findByPackageId(uuid);

        assertTrue(result.isPresent());
    }

    @Test
    void getByPackageId_with_success() {
        BBSCashPackage cashPackage = new BBSCashPackage();
        UUID uuid = UUID.randomUUID();
        cashPackage.setPackageId(uuid);
        bbsCashPackageRepository.save(cashPackage);

        BBSCashPackage result = bbsCashPackageRepository.getByPackageId(uuid);

        assertEquals(uuid, result.getPackageId());
    }
}