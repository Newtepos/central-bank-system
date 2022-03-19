package com.example.cbts.service;

import com.example.cbts.dto.BankDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.entites.Bank;
import com.example.cbts.entites.Cash;
import com.example.cbts.entites.Currency;
import com.example.cbts.entites.Location;
import com.example.cbts.repository.BankRepository;
import com.example.cbts.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CBTSService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    CurrencyRepository currencyRepository;

    public void createBank(BankDTO bankDTO) {
       Bank bank = this.convertBankDtoToEntity(bankDTO);
        bankRepository.save(bank);
    }

    public List<BankDTO> getAllBank() {
        return bankRepository.findAll()
                .stream()
                .map(this::convertBankEntityToDto)
                .collect(Collectors.toList());
    }

    private Bank convertBankDtoToEntity(BankDTO bankDTO) {
        Bank bank = new Bank();
        Location location = new Location();
        List<Cash> cashList = new ArrayList<>();
        for(CashDTO cashDTO: bankDTO.getBalance()) {
            Cash cash = new Cash();

            Optional<Currency> currency = currencyRepository.findByCurrency(cashDTO.getCurrency());
            if(currency.isPresent()) {
                cash.setCurrency(currency.get());
            }
            else
            {
                Currency currency1 = new Currency();
                currency1.setCurrency(cashDTO.getCurrency());
                cash.setCurrency(currency1);
            }

            cash.setAmount(cashDTO.getAmount());
            cashList.add(cash);
        }
        location.setLatitude(bankDTO.getLatitude());
        location.setLongitude(bankDTO.getLongitude());
        bank.setBankName(bankDTO.getBankName());
        bank.setLocation(location);
        bank.setBalance(cashList);

        return bank;
    }

    private BankDTO convertBankEntityToDto(Bank bank) {
        BankDTO bankDTO = new BankDTO();
        List<CashDTO> cashDTOList = new ArrayList<>();
        for(Cash cash: bank.getBalance()) {
            CashDTO cashDTO = new CashDTO();
            cashDTO.setAmount(cash.getAmount());
            cashDTO.setCurrency(cash.getCurrency().getCurrency());
            cashDTOList.add(cashDTO);
        }
        bankDTO.setBankName(bank.getBankName());
        bankDTO.setLatitude(bank.getLocation().getLatitude());
        bankDTO.setLongitude(bank.getLocation().getLongitude());
        bankDTO.setBalance(cashDTOList);
        return bankDTO;
    }
}
