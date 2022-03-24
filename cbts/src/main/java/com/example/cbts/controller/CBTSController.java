package com.example.cbts.controller;

import com.example.cbts.cbtspackage.CBTSGateway;
import com.example.cbts.dto.*;
import com.example.cbts.service.CBTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CBTSController {

    @Autowired
    CBTSService cbtsService;

    @Autowired
    CBTSGateway cbtsGateway;

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

    @GetMapping("/bbs-package")
    public ResponseEntity<?> getAllBBSCashPackage() {
        List<BBSCashPackageDTO> result = cbtsService.getAllBBSCashPackage();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/bbs-package")
    public ResponseEntity<?> createBBSCashPackage(@RequestBody BBSCashPackageDTO dto) {
        cbtsService.createBBSCashPackage(dto);
        return new ResponseEntity<>("Created BBS CashPackage", HttpStatus.OK);
    }

    @PutMapping("/bbs-package/{packageId}")
    public ResponseEntity<?> updateBBSCashPackage(@RequestBody DispatchActionRequest dto, @PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        cbtsService.updateBBSStatus(dto, convertedUUID);
        return new ResponseEntity<>("Update CashPackage Completed", HttpStatus.OK);
    }

    @GetMapping("/bbs-package/{packageId}")
    public ResponseEntity<?> readBBSPackage(@PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        CashDTO result = cbtsGateway.readBBSPackage(convertedUUID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
