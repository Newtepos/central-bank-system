package com.example.cbts;

import com.example.cbts.dto.BBSDTO;
import com.example.cbts.dto.BankDTO;
import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.entites.Bank;
import com.example.cbts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CBTSGateway {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    BankRepository bankRepository;

    public void createBankOnBBS(BBSDTO bbsdto) {
        restTemplate.postForObject(bbsdto.getUrl() + "/bank", bbsdto, String.class);
    }

    public void createCBTSCashPackage(CBTSCashPackageDTO dto) {
        Bank bank = bankRepository.getById(dto.getBranchId());
        restTemplate.postForObject(bank.getUrl() + "/cbts-package", dto, String.class);
    }
}
