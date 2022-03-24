package com.example.cbts.bank;

import com.example.cbts.service.UtilityService;
import com.example.cbts.cbtspackage.CBTSGateway;
import com.example.cbts.dto.BBSDTO;
import com.example.cbts.dto.BankDTO;
import com.example.cbts.entites.Bank;
import com.example.cbts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankService {

    @Autowired
    UtilityService utilityService;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CBTSGateway bbsGateway;

    public void createBank(BankDTO bankDTO) {
        //Validate Input
        utilityService.validateBankExits(bankDTO.getBankName());

        //Create Bank on CBTS
        Bank bank = utilityService.convertBankDtoToEntity(bankDTO);
        bankRepository.save(bank);

        //Create Bank on BBS
        Optional<Bank> query = bankRepository.findByBankName(bankDTO.getBankName());
        BBSDTO bbsdto = new BBSDTO();
        bbsdto.setBankName(bankDTO.getBankName());
        bbsdto.setCbtsKey(query.get().getId());
        bbsdto.setLatitude(bankDTO.getLatitude());
        bbsdto.setLongitude(bankDTO.getLongitude());
        bbsdto.setBalance(bankDTO.getBalance());
        bbsdto.setUrl(bankDTO.getUrl());
        bbsGateway.createBankOnBBS(bbsdto);
    }

    public List<BankDTO> getAllBank() {
        return bankRepository.findAll()
                .stream()
                .map(bank -> utilityService.convertBankEntityToDto(bank))
                .collect(Collectors.toList());
    }

    public BankDTO getBankById(Long id) {
        //validate Input
        utilityService.bankNotFound(id);

        Bank queryResult = bankRepository.getById(id);
        return utilityService.convertBankEntityToDto(queryResult);
    }
}
