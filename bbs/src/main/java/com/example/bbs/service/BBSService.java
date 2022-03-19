package com.example.bbs.service;

import com.example.bbs.dto.BankDTO;
import com.example.bbs.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BBSService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    UtilityService utilityService;

    public void createBranchBank(BankDTO dto) {
        //Validate
        utilityService.validateBankExits(dto.getBankName(), dto.getCbtsKey());

        bankRepository.save(utilityService.covertBankDtoToEntity(dto));
    }
}
