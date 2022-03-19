package com.example.bbs.controller;

import com.example.bbs.dto.BankDTO;
import com.example.bbs.service.BBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BBSController {

    @Autowired
    BBSService bbsService;

    @PostMapping("/bank")
    public ResponseEntity<?> createBrandBank(@RequestBody BankDTO bankDTO) {
        bbsService.createBranchBank(bankDTO);
        return new ResponseEntity<>("Branch-Bank Created", HttpStatus.OK);
    }
}
