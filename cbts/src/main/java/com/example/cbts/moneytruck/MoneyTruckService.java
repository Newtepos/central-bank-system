package com.example.cbts.moneytruck;

import com.example.cbts.dto.MoneyTruckDTO;
import com.example.cbts.entites.Location;
import com.example.cbts.entites.MoneyTruck;
import com.example.cbts.repository.MoneyTruckRepository;
import com.example.cbts.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MoneyTruckService {

    @Autowired
    MoneyTruckRepository moneyTruckRepository;

    @Autowired
    UtilityService utilityService;

    public void createMoneyTruck(MoneyTruckDTO moneyTruckDTO) {
        //Validate Input
        utilityService.validateTruckExits(moneyTruckDTO.getTruckName());

        MoneyTruck moneyTruck = utilityService.covertMoneyTruckDtoToEntity(moneyTruckDTO);
        moneyTruckRepository.save(moneyTruck);
    }

    public void updateMoneyTruckLocation(MoneyTruckDTO moneyTruckDTO) {
        //Validate Input
        utilityService.moneyTruckNotFound(moneyTruckDTO.getId());

        MoneyTruck moneyTruckDB = moneyTruckRepository.getById(moneyTruckDTO.getId());
        Location location = new Location();
        location.setLatitude(moneyTruckDTO.getLatitude());
        location.setLongitude(moneyTruckDTO.getLongitude());
        location.setTimestamp(moneyTruckDTO.getTimestamp());
        moneyTruckDB.addLocation(location);
        moneyTruckRepository.save(moneyTruckDB);
    }

    public List<MoneyTruckDTO> getAllMoneyTruckLastLocation() {
        List<MoneyTruckDTO> moneyTruckDTOList = new ArrayList<>();
        List<MoneyTruck> queryResult = moneyTruckRepository.findAll();
        for(MoneyTruck moneyTruck: queryResult) {
            MoneyTruckDTO moneyTruckDTO = utilityService.covertMoneyTruckEntityToDto(moneyTruck);
            moneyTruckDTOList.add(moneyTruckDTO);
        }

        return moneyTruckDTOList;
    }

    public MoneyTruckDTO getMoneyTruckLocationById(long id) {
        //validate input id
        utilityService.moneyTruckNotFound(id);

        return utilityService.covertMoneyTruckEntityToDto(moneyTruckRepository.getById(id));
    }
}
