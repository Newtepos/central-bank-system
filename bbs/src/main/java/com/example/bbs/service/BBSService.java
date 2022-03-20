package com.example.bbs.service;

import com.example.bbs.BBSGateway;
import com.example.bbs.dto.BBSCashPackageDTO;
import com.example.bbs.dto.BankDTO;
import com.example.bbs.dto.CBTSCashPackageDTO;
import com.example.bbs.entites.BBSCashPackage;
import com.example.bbs.repository.BBSCashPackageRepository;
import com.example.bbs.repository.BankRepository;
import com.example.bbs.repository.CBTSCashPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BBSService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    @Autowired
    CBTSCashPackageRepository cbtsCashPackageRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    BBSGateway bbsGateway;

    public void createBranchBank(BankDTO dto) {
        //Validate
        utilityService.validateBankExits(dto.getBankName(), dto.getCbtsKey());

        bankRepository.save(utilityService.covertBankDtoToEntity(dto));
    }

    public void createBBSCashPackage(BBSCashPackageDTO bbsCashPackageDTO) {
        UUID cashPackageId = UUID.randomUUID();
        bbsCashPackageDTO.setPackageId(cashPackageId);
        bbsCashPackageRepository.save(utilityService.covertBBSCashPackageDtoToEntity(bbsCashPackageDTO));

        //send BBSPackage to CBTS
        bbsGateway.createBBSCashPackage(bbsCashPackageDTO);
    }

    public void createCBTSCashPackage(CBTSCashPackageDTO dto) {
        cbtsCashPackageRepository.save(utilityService.covertCBTSCashPackageDtoToEntity(dto));
    }
}
