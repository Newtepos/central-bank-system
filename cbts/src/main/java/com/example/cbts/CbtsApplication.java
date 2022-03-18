package com.example.cbts;

import com.example.cbts.entites.Currency;
import com.example.cbts.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class CbtsApplication {

	@Autowired
	CurrencyRepository currencyRepository;

	@PostConstruct
	public void initData() {
		Currency usd = new Currency("USD");
		Currency thb = new Currency("THB");
		currencyRepository.save(usd);
		currencyRepository.save(thb);
	}

	public static void main(String[] args) {
		SpringApplication.run(CbtsApplication.class, args);
	}

}
