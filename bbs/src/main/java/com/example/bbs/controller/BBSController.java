package com.example.bbs.controller;

import com.example.bbs.dto.BBSCashPackageDTO;
import com.example.bbs.dto.BankDTO;
import com.example.bbs.dto.CBTSCashPackageDTO;
import com.example.bbs.dto.CashDTO;
import com.example.bbs.service.BBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BBSController {

    @Autowired
    BBSService bbsService;

    @PostMapping("/bank")
    public ResponseEntity<?> createBrandBank(@RequestBody BankDTO bankDTO) {
        bbsService.createBranchBank(bankDTO);
        return new ResponseEntity<>("Branch-Bank Created", HttpStatus.OK);
    }

    @PostMapping("/bbs-package")
    public ResponseEntity<?> createBBSPackage(@RequestBody BBSCashPackageDTO bbsCashPackageDTO) {
        bbsService.createBBSCashPackage(bbsCashPackageDTO);
        return new ResponseEntity<>("Package Created", HttpStatus.OK);
    }

    @PostMapping("/cbts-package")
    public ResponseEntity<?> createCBTSPackage(@RequestBody CBTSCashPackageDTO dto) {
        bbsService.createCBTSCashPackage(dto);
        return new ResponseEntity<>("Package Created", HttpStatus.OK);
    }

    @GetMapping("/bbs-package/{packageId}")
    public ResponseEntity<?> readCashBBS(@PathVariable String packageId ){
        UUID convertedUUID = UUID.fromString(packageId);
        CashDTO result = bbsService.readBBSCashPackage(convertedUUID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
