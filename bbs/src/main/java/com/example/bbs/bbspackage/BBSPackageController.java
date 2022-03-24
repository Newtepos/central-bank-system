package com.example.bbs.bbspackage;

import com.example.bbs.dto.BBSCashPackageDTO;
import com.example.bbs.dto.CashDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class BBSPackageController {

    @Autowired
    BBSPackageService bbsPackageService;

    @PostMapping("/bbs-package")
    public ResponseEntity<?> createBBSPackage(@RequestBody BBSCashPackageDTO bbsCashPackageDTO) {
        bbsPackageService.createBBSCashPackage(bbsCashPackageDTO);
        return new ResponseEntity<>("Package Created", HttpStatus.OK);
    }

    @GetMapping("/bbs-package/{packageId}")
    public ResponseEntity<?> readCashBBS(@PathVariable String packageId ){
        UUID convertedUUID = UUID.fromString(packageId);
        CashDTO result = bbsPackageService.readBBSCashPackage(convertedUUID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
