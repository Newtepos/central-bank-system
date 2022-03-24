package com.example.cbts.bbspackage;

import com.example.cbts.dto.BBSDTO;
import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.entites.BBSCashPackage;
import com.example.cbts.entites.Bank;
import com.example.cbts.repository.BBSCashPackageRepository;
import com.example.cbts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class BBSGateway {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    public void createBankOnBBS(BBSDTO bbsdto) {
        restTemplate.postForObject(bbsdto.getUrl() + "/bank", bbsdto, String.class);
    }

    public void createCBTSCashPackage(CBTSCashPackageDTO dto) {
        Bank bank = bankRepository.getById(dto.getBranchId());
        restTemplate.postForObject(bank.getUrl() + "/cbts-package", dto, String.class);
    }

    public CashDTO readBBSPackage(UUID packageId) {
        BBSCashPackage queryBBS = bbsCashPackageRepository.getByPackageId(packageId);
        String url = queryBBS.getSender().getUrl();
        CashDTO cashDTO =  restTemplate.getForObject(url + "/bbs-package/" + packageId, CashDTO.class);
        return cashDTO;
    }
}
