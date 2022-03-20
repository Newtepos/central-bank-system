package com.example.bbs.service;

import com.example.bbs.dto.BBSCashPackageDTO;
import com.example.bbs.dto.BankDTO;
import com.example.bbs.entites.BBSCashPackage;
import com.example.bbs.repository.BBSCashPackageRepository;
import com.example.bbs.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BBSService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    @Autowired
    UtilityService utilityService;

    public void createBranchBank(BankDTO dto) {
        //Validate
        utilityService.validateBankExits(dto.getBankName(), dto.getCbtsKey());

        bankRepository.save(utilityService.covertBankDtoToEntity(dto));
    }

    public void createBBSCashPackage(BBSCashPackageDTO bbsCashPackageDTO) {
        bbsCashPackageRepository.save(utilityService.covertBBSCashPackageDtoToEntity(bbsCashPackageDTO));
    }
}
