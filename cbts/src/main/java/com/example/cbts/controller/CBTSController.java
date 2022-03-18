package com.example.cbts.controller;

import com.example.cbts.request.BankRequest;
import com.example.cbts.service.CBTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CBTSController {

    @Autowired
    CBTSService cbtsService;

    @PostMapping("/bank")
    public ResponseEntity createBank(@RequestBody BankRequest bankRequest) {
        cbtsService.createBank(bankRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
