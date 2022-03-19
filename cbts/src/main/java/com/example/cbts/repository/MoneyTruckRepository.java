package com.example.cbts.repository;

import com.example.cbts.entites.MoneyTruck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MoneyTruckRepository extends JpaRepository<MoneyTruck, Long> {
        Optional<MoneyTruck> findByTruckName(String name);
}
