package com.example.cbts.cbtspackage;

import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.DispatchActionRequest;
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

    @PostMapping("/cbts-package")
    public ResponseEntity<?> createCBTSCashPackage(@RequestBody CBTSCashPackageDTO dto) {
        cbtsService.createCBTSCashPackage(dto);
        return new ResponseEntity<>("CashPackage Created", HttpStatus.OK);
    }

    @PutMapping("/cbts-package/{packageId}")
    public ResponseEntity<?> updateCBTSCashPackage(@RequestBody DispatchActionRequest dto, @PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        cbtsService.updateCBTSStatus(dto, convertedUUID);
        return new ResponseEntity<>("Update CashPackage Completed", HttpStatus.OK);
    }

    @GetMapping("/cbts-package")
    public ResponseEntity<?> getAllCBTSCashPackage() {
        List<CBTSCashPackageDTO> result = cbtsService.getAllCBTSCashPackage();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/cbts-package/{packageId}")
    public ResponseEntity<?> readCashBBS(@PathVariable String packageId ){
        UUID convertedUUID = UUID.fromString(packageId);
        CashDTO result = cbtsService.readCBTSPackage(convertedUUID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
