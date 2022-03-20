package com.example.cbts.service;

import com.example.cbts.CBTSGateway;
import com.example.cbts.dto.*;
import com.example.cbts.entites.*;
import com.example.cbts.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    @Autowired
    CoreBankingService coreBankingService;

    @Autowired
    CBTSGateway cbtsGateway;

    public void createBank(BankDTO bankDTO) {
        //Validate Input
        utilityService.validateBankExits(bankDTO.getBankName());

        //Create Bank on CBTS
       Bank bank = utilityService.convertBankDtoToEntity(bankDTO);
       bankRepository.save(bank);

       //Create Bank on BBS
        Optional<Bank> query = bankRepository.findByBankName(bankDTO.getBankName());
        BBSDTO bbsdto = new BBSDTO();
        bbsdto.setBankName(bankDTO.getBankName());
        bbsdto.setCbtsKey(query.get().getId());
        bbsdto.setLatitude(bankDTO.getLatitude());
        bbsdto.setLongitude(bankDTO.getLongitude());
        bbsdto.setBalance(bankDTO.getBalance());
        bbsdto.setUrl(bankDTO.getUrl());
        cbtsGateway.createBankOnBBS(bbsdto);
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

        UUID cashPackageID = UUID.randomUUID();
        dto.setPackageId(cashPackageID);
        CBTSCashPackage cashPackage = utilityService.covertCBTSCashPackageDtoToEntity(dto);
        cbtsCashPackageRepository.save(cashPackage);

        //Update CashPackage to BB System
        cbtsGateway.createCBTSCashPackage(dto);
    }

    public void updateCBTSStatus(DispatchActionRequest dto, UUID packageId) {
        //validate CBTSPackage
        utilityService.validateCBTSCashPackage(packageId);

        CBTSCashPackage queryResult = cbtsCashPackageRepository.getByPackageId(packageId);

        //dispatch action
        if(dto.getMethod().equals("sent")) {
            queryResult.setSendStatus(true);
            queryResult.setSendTime(dto.getActionTime());

            //update Central Balance
            coreBankingService.decreaseCentralBankBalance(queryResult);
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

    public List<BBSCashPackageDTO> getAllBBSCashPackage() {
        List<BBSCashPackageDTO> bbsCashPackageDTOS = new ArrayList<>();
        List<BBSCashPackage> queryResult = bbsCashPackageRepository.findAll();
        for(BBSCashPackage cashPackage: queryResult) {
            bbsCashPackageDTOS.add(utilityService.covertBBSCashPackageEntityToDto(cashPackage));
        }

        return bbsCashPackageDTOS;
    }

    public void createBBSCashPackage(BBSCashPackageDTO dto) {
        //validate input
        utilityService.bankNotFound(dto.getBranchId());

        BBSCashPackage cashPackage = utilityService.covertBBSCashPackageDtoToEntity(dto);
        bbsCashPackageRepository.save(cashPackage);
    }

    public void updateBBSStatus(DispatchActionRequest dto, UUID packageId) {
        //validate CBTSPackage
        utilityService.validateBBSCashPackage(packageId);

        BBSCashPackage queryResult = bbsCashPackageRepository.getByPackageId(packageId);

        //dispatch action
        if(dto.getMethod().equals("sent")) {
            queryResult.setSendStatus(true);
            queryResult.setSendTime(dto.getActionTime());
        }

        else if(dto.getMethod().equals("received")) {
            queryResult.setReceiveStatus(true);
            queryResult.setReceivedTime(dto.getActionTime());
        }
        bbsCashPackageRepository.save(queryResult);
    }
}
