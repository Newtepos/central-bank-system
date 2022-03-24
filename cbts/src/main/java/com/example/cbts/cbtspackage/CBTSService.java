package com.example.cbts.cbtspackage;

import com.example.cbts.bbspackage.BBSGateway;
import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.DispatchActionRequest;
import com.example.cbts.entites.CBTSCashPackage;
import com.example.cbts.repository.CBTSCashPackageRepository;
import com.example.cbts.service.CoreBankingService;
import com.example.cbts.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CBTSService {

    @Autowired
    UtilityService utilityService;

    @Autowired
    CBTSCashPackageRepository cbtsCashPackageRepository;

    @Autowired
    CoreBankingService coreBankingService;

    @Autowired
    BBSGateway bbsGateway;

    public void createCBTSCashPackage(CBTSCashPackageDTO dto) {
        //validate input
        utilityService.bankNotFound(dto.getBranchId());
        utilityService.validateCenterBankFund(dto.getCurrency(), dto.getAmount());

        UUID cashPackageID = UUID.randomUUID();
        dto.setPackageId(cashPackageID);
        CBTSCashPackage cashPackage = utilityService.covertCBTSCashPackageDtoToEntity(dto);
        cbtsCashPackageRepository.save(cashPackage);

        //Update CashPackage to BB System
        bbsGateway.createCBTSCashPackage(dto);
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

    public CashDTO readCBTSPackage(UUID packageId) {
        CashDTO cashDTO = new CashDTO();
        CBTSCashPackage query = cbtsCashPackageRepository.getByPackageId(packageId);
        cashDTO.setAmount(query.getCash().getAmount());
        cashDTO.setCurrency(query.getCash().getCurrency().getCurrency());
        return cashDTO;
    }
}
