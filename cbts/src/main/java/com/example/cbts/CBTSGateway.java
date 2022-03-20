package com.example.cbts;

import com.example.cbts.dto.BBSDTO;
import com.example.cbts.dto.BankDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CBTSGateway {

    @Autowired
    private RestTemplate restTemplate;

    public void createBankOnBBS(BBSDTO bbsdto) {
        restTemplate.postForObject(bbsdto.getUrl() + "/bank", bbsdto, String.class);
    }
}
