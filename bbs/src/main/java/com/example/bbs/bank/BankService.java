package com.example.bbs.bank;

import com.example.bbs.dto.BankDTO;
import com.example.bbs.repository.BankRepository;
import com.example.bbs.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    @Autowired
    UtilityService utilityService;

    @Autowired
    BankRepository bankRepository;

    public void createBranchBank(BankDTO dto) {
        //Validate
        utilityService.validateBankExits(dto.getBankName(), dto.getCbtsKey());

        bankRepository.save(utilityService.covertBankDtoToEntity(dto));
    }
}
