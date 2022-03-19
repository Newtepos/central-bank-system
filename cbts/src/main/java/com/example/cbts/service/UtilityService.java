package com.example.cbts.service;

import com.example.cbts.dto.BankDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.MoneyTruckDTO;
import com.example.cbts.entites.*;
import com.example.cbts.exception.CannotFindDataException;
import com.example.cbts.exception.DataAlreadyExitsException;
import com.example.cbts.repository.BankRepository;
import com.example.cbts.repository.CurrencyRepository;
import com.example.cbts.repository.MoneyTruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtilityService {

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    BankRepository bankRepository;

    @Autowired
    MoneyTruckRepository moneyTruckRepository;

    public Bank convertBankDtoToEntity(BankDTO bankDTO) {
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

    public BankDTO convertBankEntityToDto(Bank bank) {
        BankDTO bankDTO = new BankDTO();
        List<CashDTO> cashDTOList = new ArrayList<>();
        for(Cash cash: bank.getBalance()) {
            CashDTO cashDTO = new CashDTO();
            cashDTO.setAmount(cash.getAmount());
            cashDTO.setCurrency(cash.getCurrency().getCurrency());
            cashDTOList.add(cashDTO);
        }
        bankDTO.setId(bank.getId());
        bankDTO.setBankName(bank.getBankName());
        bankDTO.setLatitude(bank.getLocation().getLatitude());
        bankDTO.setLongitude(bank.getLocation().getLongitude());
        bankDTO.setBalance(cashDTOList);
        return bankDTO;
    }

    public MoneyTruck covertMoneyTruckDtoToEntity(MoneyTruckDTO moneyTruckDTO) {
        MoneyTruck moneyTruck = new MoneyTruck();
        List<Location> locations = new ArrayList<>();
        Location location = new Location();
        location.setLatitude(moneyTruckDTO.getLatitude());
        location.setLongitude(moneyTruckDTO.getLongitude());
        location.setTimestamp(moneyTruckDTO.getTimestamp());
        locations.add(location);
        moneyTruck.setTruckName(moneyTruckDTO.getTruckName());
        moneyTruck.setLocations(locations);
        return moneyTruck;
    }

    public MoneyTruckDTO covertMoneyTruckEntityToDto(MoneyTruck moneyTruck) {
        MoneyTruckDTO moneyTruckDTO = new MoneyTruckDTO();
        moneyTruckDTO.setTruckName(moneyTruck.getTruckName());
        moneyTruckDTO.setId(moneyTruck.getId());
        int lastLocation = moneyTruck.getLocations().size();
        moneyTruckDTO.setLatitude(moneyTruck.getLocations().get(lastLocation-1).getLatitude());
        moneyTruckDTO.setLongitude(moneyTruck.getLocations().get(lastLocation-1).getLongitude());
        moneyTruckDTO.setTimestamp(moneyTruck.getLocations().get(lastLocation-1).getTimestamp());
        return moneyTruckDTO;
    }

    public void validateBankExits(String name) {
        if(bankRepository.findByBankName(name).isPresent()){
            throw new DataAlreadyExitsException("Bank Already Exits");
        }
    }

    public void validateTruckExits(String name) {
        if(moneyTruckRepository.findByTruckName(name).isPresent()) {
            throw new DataAlreadyExitsException("MoneyTruck Already Exits");
        }
    }

    public void bankNotFound(long id) {
        if(bankRepository.findById(id).isEmpty()) {
            throw new CannotFindDataException("Cannot find Bank with ID:" + id);
        }
    }

    public void MoneyTruckNotFound(Long id) {
        if(moneyTruckRepository.findById(id).isEmpty()) {
            throw new CannotFindDataException("Cannot find MoneyTruck with ID:" + id);
        }
    }

}
