package com.example.cbts.bank;

import com.example.cbts.bbspackage.BBSService;
import com.example.cbts.dto.BankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankController {

    @Autowired
    BankService bankService;

    @PostMapping("/bank")
    public ResponseEntity<?> createBank(@RequestBody BankDTO bankDTO) {
        bankService.createBank(bankDTO);
        return new ResponseEntity<>(bankDTO, HttpStatus.OK);
    }

    @GetMapping("/bank")
    public ResponseEntity<?> getAllBank() {
        List<BankDTO> result = bankService.getAllBank();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/bank/{id}")
    public ResponseEntity<?> getBankById(@PathVariable long id) {
        BankDTO result = bankService.getBankById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
