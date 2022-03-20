package com.example.cbts.repository;

import com.example.cbts.entites.MoneyTruck;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MoneyTruckRepositoryTest {

    @Autowired
    private MoneyTruckRepository moneyTruckRepository;

    @Test
    void findByTruckName_with_success() {
        //Arrange
        MoneyTruck moneyTruck = new MoneyTruck();
        moneyTruck.setTruckName("Truck 1");
        moneyTruckRepository.save(moneyTruck);

        //Act
        Optional<MoneyTruck> result = moneyTruckRepository.findByTruckName("Truck 1");

        //Assert
        assertTrue(result.isPresent());
    }
}