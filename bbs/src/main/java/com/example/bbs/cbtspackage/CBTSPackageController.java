package com.example.bbs.cbtspackage;

import com.example.bbs.dto.CBTSCashPackageDTO;
import com.example.bbs.dto.CashDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class CBTSPackageController {

    @Autowired
    CBTSPackageService cbtsPackageService;

    @Autowired
    CBTSGateway bbsGateway;

    @PostMapping("/cbts-package")
    public ResponseEntity<?> createCBTSPackage(@RequestBody CBTSCashPackageDTO dto) {
        cbtsPackageService.createCBTSCashPackage(dto);
        return new ResponseEntity<>("Package Created", HttpStatus.OK);
    }

    @GetMapping("/cbts-package/{packageId}")
    public ResponseEntity<?> readBBSPackage(@PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        CashDTO result = bbsGateway.readCBTSPackage(convertedUUID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
