package com.example.bbs.bbspackage;

import com.example.bbs.cbtspackage.CBTSGateway;
import com.example.bbs.dto.BBSCashPackageDTO;
import com.example.bbs.dto.CashDTO;
import com.example.bbs.entites.BBSCashPackage;
import com.example.bbs.repository.BBSCashPackageRepository;
import com.example.bbs.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BBSPackageService {

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    CBTSGateway bbsGateway;

    public void createBBSCashPackage(BBSCashPackageDTO bbsCashPackageDTO) {
        UUID cashPackageId = UUID.randomUUID();
        bbsCashPackageDTO.setPackageId(cashPackageId);
        bbsCashPackageRepository.save(utilityService.covertBBSCashPackageDtoToEntity(bbsCashPackageDTO));

        //send BBSPackage to CBTS
        bbsGateway.createBBSCashPackage(bbsCashPackageDTO);
    }

    public CashDTO readBBSCashPackage(UUID packageId) {
        CashDTO cashDTO = new CashDTO();
        BBSCashPackage query = bbsCashPackageRepository.getByPackageId(packageId);
        cashDTO.setAmount(query.getCash().getAmount());
        cashDTO.setCurrency(query.getCash().getCurrency().getCurrency());

        return cashDTO;
    }
}
