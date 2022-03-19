package com.example.cbts.service;

import com.example.cbts.dto.BankDTO;
import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.MoneyTruckDTO;
import com.example.cbts.entites.*;
import com.example.cbts.repository.BankRepository;
import com.example.cbts.repository.CBTSCashPackageRepository;
import com.example.cbts.repository.CurrencyRepository;
import com.example.cbts.repository.MoneyTruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CBTSService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    MoneyTruckRepository moneyTruckRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    CBTSCashPackageRepository cbtsCashPackageRepository;

    public void createBank(BankDTO bankDTO) {
        //Validate Input
        utilityService.validateBankExits(bankDTO.getBankName());

       Bank bank = utilityService.convertBankDtoToEntity(bankDTO);
        bankRepository.save(bank);
    }

    public List<BankDTO> getAllBank() {
        return bankRepository.findAll()
                .stream()
                .map(bank -> utilityService.convertBankEntityToDto(bank))
                .collect(Collectors.toList());
    }

    public BankDTO getBankById(Long id) {
        //validate Input
        utilityService.bankNotFound(id);

        Bank queryResult = bankRepository.getById(id);
        return utilityService.convertBankEntityToDto(queryResult);
    }

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

    public void createCBTSCashPackage(CBTSCashPackageDTO dto) {
        //validate input
        utilityService.bankNotFound(dto.getBranchId());
        utilityService.validateCenterBankFund(dto.getCurrency(), dto.getAmount());

        CBTSCashPackage cashPackage = utilityService.covertCBTSCashPackageDtoToEntity(dto);
        cbtsCashPackageRepository.save(cashPackage);
    }

    public void updateCBTSStatus(CBTSCashPackageDTO dto) {
        CBTSCashPackage queryResult = cbtsCashPackageRepository.getByPackageId(dto.getPackageId());
        //dispatch action
        if(dto.getMethod().equals("sent")) {
            queryResult.setSendStatus(true);
            queryResult.setSendTime(dto.getActionTime());
        }
        else if(dto.getMethod().equals("received")) {
            queryResult.setReceiveStatus(true);
            queryResult.setReceivedTime(dto.getActionTime());
        }
        cbtsCashPackageRepository.save(queryResult);
    }

    public List<CBTSCashPackageDTO> getAllCBTSCashPackage() {
        List<CBTSCashPackageDTO> cbtsCashPackageDTOS = new ArrayList<>();
        List<CBTSCashPackage> queryResult = cbtsCashPackageRepository.findAll();
        for(CBTSCashPackage cashPackage: queryResult) {
            cbtsCashPackageDTOS.add(utilityService.covertCBTSCashPackageEntityToDto(cashPackage));
        }

        return cbtsCashPackageDTOS;
    }

}
