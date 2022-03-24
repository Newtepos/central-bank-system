package com.example.cbts.bbspackage;

import com.example.cbts.cbtspackage.CBTSService;
import com.example.cbts.dto.BBSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.DispatchActionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BBSController {

    @Autowired
    BBSService bbsService;

    @GetMapping("/bbs-package")
    public ResponseEntity<?> getAllBBSCashPackage() {
        List<BBSCashPackageDTO> result = bbsService.getAllBBSCashPackage();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/bbs-package")
    public ResponseEntity<?> createBBSCashPackage(@RequestBody BBSCashPackageDTO dto) {
        bbsService.createBBSCashPackage(dto);
        return new ResponseEntity<>("Created BBS CashPackage", HttpStatus.OK);
    }

    @PutMapping("/bbs-package/{packageId}")
    public ResponseEntity<?> updateBBSCashPackage(@RequestBody DispatchActionRequest dto, @PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        bbsService.updateBBSStatus(dto, convertedUUID);
        return new ResponseEntity<>("Update CashPackage Completed", HttpStatus.OK);
    }

    @GetMapping("/bbs-package/{packageId}")
    public ResponseEntity<?> readBBSPackage(@PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        CashDTO result = bbsService.readBBSPackage(convertedUUID);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
