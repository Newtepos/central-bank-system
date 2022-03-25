package com.example.cbts.cbtspackage;

import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.DispatchActionRequest;
import com.example.cbts.dto.QRCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    @GetMapping("/cbts-package/{packageId}/qr-code")
    public ResponseEntity<?> getQRCode(@PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
       QRCodeDTO qrcode = cbtsService.createCBTSQRCODE(convertedUUID);
       return new ResponseEntity<>(qrcode, HttpStatus.OK);
    }

    @GetMapping("/cbts-package/{packageId}/qr-code/send/true")
    public ResponseEntity<?> sentCBTSPackage(@PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        DispatchActionRequest actionRequest = new DispatchActionRequest();
        Timestamp sendTime = new Timestamp(System.currentTimeMillis());
        actionRequest.setMethod("sent");
        actionRequest.setActionTime(sendTime);
        cbtsService.updateCBTSStatus(actionRequest, convertedUUID);
        return new ResponseEntity<>("package sent", HttpStatus.OK);
    }

    @GetMapping("cbts-package/{packageId}/qr-code/receive/true")
    public ResponseEntity<?> receiveCBTSPackage(@PathVariable String packageId) {
        UUID convertedUUID = UUID.fromString(packageId);
        DispatchActionRequest actionRequest = new DispatchActionRequest();
        Timestamp sendTime = new Timestamp(System.currentTimeMillis());
        actionRequest.setMethod("received");
        actionRequest.setActionTime(sendTime);
        cbtsService.updateCBTSStatus(actionRequest, convertedUUID);
        return new ResponseEntity<>("package received", HttpStatus.OK);
    }
}
