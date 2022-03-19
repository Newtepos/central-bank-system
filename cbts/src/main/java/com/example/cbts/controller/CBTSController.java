package com.example.cbts.controller;

import com.example.cbts.dto.BankDTO;
import com.example.cbts.service.CBTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
