package com.example.bbs.controller;

import com.example.bbs.BBSGateway;
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

    @Autowired
    BBSGateway bbsGateway;

    @PostMapping("/bank")
    public ResponseEntity<?> createBrandBank(@RequestBody BankDTO bankDTO) {
        bbsService.createBranchBank(bankDTO);
        return new ResponseEntity<>("Branch-Bank Created", HttpStatus.OK);
    }
}
