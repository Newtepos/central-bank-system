package com.example.bbs.bank;

import com.example.bbs.dto.BankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

    @Autowired
    BankService bankService;

    @PostMapping("/bank")
    public ResponseEntity<?> createBrandBank(@RequestBody BankDTO bankDTO) {
        bankService.createBranchBank(bankDTO);
        return new ResponseEntity<>("Branch-Bank Created", HttpStatus.OK);
    }
}
