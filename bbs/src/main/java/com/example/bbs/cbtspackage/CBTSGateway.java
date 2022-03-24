package com.example.bbs.cbtspackage;

import com.example.bbs.dto.BBSCashPackageDTO;
import com.example.bbs.dto.CashDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class CBTSGateway {

    @Autowired
    private RestTemplate restTemplate;

    public void createBBSCashPackage(BBSCashPackageDTO dto) {
        restTemplate.postForObject("http://localhost:8080/bbs-package", dto, String.class);
    }

    public CashDTO readCBTSPackage(UUID packageId) {
        CashDTO cashDTO =  restTemplate.getForObject( "http://localhost:8080/cbts-package/" + packageId, CashDTO.class);
        return cashDTO;
    }
}
