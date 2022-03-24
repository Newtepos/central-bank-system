package com.example.cbts.moneytruck;

import com.example.cbts.dto.MoneyTruckDTO;
import com.example.cbts.service.CBTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoneyTruckController {

    @Autowired
    MoneyTruckService moneyTruckService;

    @PostMapping("/money-truck")
    public ResponseEntity<?> createTruck(@RequestBody MoneyTruckDTO moneyTruckDTO) {
        moneyTruckService.createMoneyTruck(moneyTruckDTO);
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    @PutMapping("/money-truck")
    public ResponseEntity<?> updateTruck(@RequestBody MoneyTruckDTO moneyTruckDTO) {
        moneyTruckService.updateMoneyTruckLocation(moneyTruckDTO);
        return new ResponseEntity<>("Updated", HttpStatus.OK);
    }

    @GetMapping("/money-truck/current")
    public ResponseEntity<?> getAllMoneyTruckLocation() {
        List<MoneyTruckDTO> result = moneyTruckService.getAllMoneyTruckLastLocation();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/money-truck/{id}/current")
    public ResponseEntity<?> getMoneyTruckRecentLocationById(@PathVariable long id) {
        MoneyTruckDTO result = moneyTruckService.getMoneyTruckLocationById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
