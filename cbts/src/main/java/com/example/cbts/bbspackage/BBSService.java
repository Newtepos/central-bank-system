package com.example.cbts.bbspackage;

import com.example.cbts.service.UtilityService;
import com.example.cbts.dto.BBSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.DispatchActionRequest;
import com.example.cbts.entites.BBSCashPackage;
import com.example.cbts.repository.BBSCashPackageRepository;
import com.example.cbts.service.CoreBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BBSService {

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    CoreBankingService coreBankingService;

    @Autowired
    RestTemplate restTemplate;


    public CashDTO readBBSPackage(UUID packageId) {
        BBSCashPackage queryBBS = bbsCashPackageRepository.getByPackageId(packageId);
        String url = queryBBS.getSender().getUrl();
        CashDTO cashDTO =  restTemplate.getForObject(url + "/bbs-package/" + packageId, CashDTO.class);
        return cashDTO;
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

            //update Central Balance
            coreBankingService.increaseCentralBankBalance(queryResult);
        }
        bbsCashPackageRepository.save(queryResult);
    }
}
