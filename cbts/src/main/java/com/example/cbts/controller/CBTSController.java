package com.example.cbts.controller;

import com.example.cbts.dto.BankDTO;
import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.dto.MoneyTruckDTO;
import com.example.cbts.service.CBTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CBTSController {

    @Autowired
    CBTSService cbtsService;

    @PostMapping("/bank")
    public ResponseEntity<?> createBank(@RequestBody BankDTO bankDTO) {
        cbtsService.createBank(bankDTO);
        return new ResponseEntity<>(bankDTO,HttpStatus.OK);
    }

    @GetMapping("/bank")
    public ResponseEntity<?> getAllBank() {
        List<BankDTO> result = cbtsService.getAllBank();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/bank/{id}")
    public ResponseEntity<?> getBankById(@PathVariable long id) {
        BankDTO result = cbtsService.getBankById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/money-truck")
    public ResponseEntity<?> createTruck(@RequestBody MoneyTruckDTO moneyTruckDTO) {
       cbtsService.createMoneyTruck(moneyTruckDTO);
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    @PutMapping("/money-truck")
    public ResponseEntity<?> updateTruck(@RequestBody MoneyTruckDTO moneyTruckDTO) {
        cbtsService.updateMoneyTruckLocation(moneyTruckDTO);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @GetMapping("/money-truck/current")
    public ResponseEntity<?> getAllMoneyTruckLocation() {
        List<MoneyTruckDTO> result = cbtsService.getAllMoneyTruckLastLocation();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/money-truck/{id}/current")
    public ResponseEntity<?> getMoneyTruckRecentLocationById(@PathVariable long id) {
        MoneyTruckDTO result = cbtsService.getMoneyTruckLocationById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/cbts-package")
    public ResponseEntity<?> createCBTSCashPackage(@RequestBody CBTSCashPackageDTO dto) {
        cbtsService.createCBTSCashPackage(dto);
        return new ResponseEntity<>("CashPackage Created", HttpStatus.OK);
    }

}
