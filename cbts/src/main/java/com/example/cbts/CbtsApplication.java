package com.example.cbts;

import com.example.cbts.entites.Bank;
import com.example.cbts.entites.Cash;
import com.example.cbts.entites.Currency;
import com.example.cbts.entites.Location;
import com.example.cbts.repository.BankRepository;
import com.example.cbts.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CbtsApplication {

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	BankRepository bankRepository;

	@PostConstruct
	public void initData() {

		//IniCenterBank
		Bank bank = new Bank();
		Currency usd = new Currency("USD");
		Currency thb = new Currency("THB");
		Location location = new Location(13.9106, 100.5515);
		location.setTimestamp(new Timestamp(System.currentTimeMillis()));
		List<Cash> cashList = new ArrayList<>();
		List<Currency> queryResult = currencyRepository.findAll();
		Cash thbCash = new Cash(new BigDecimal(2000000000), thb);
		Cash usdCash = new Cash(new BigDecimal(500000000), usd);
		cashList.add(thbCash);
		cashList.add(usdCash);
		bank.setBankName("CentralBank");
		bank.setLocation(location);
		bank.setBalance(cashList);
		bank.setUrl("http://localhost:8080");
		bankRepository.save(bank);
	}

	public static void main(String[] args) {
		SpringApplication.run(CbtsApplication.class, args);
	}

}
