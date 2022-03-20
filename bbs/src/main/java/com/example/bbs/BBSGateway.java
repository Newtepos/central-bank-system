package com.example.bbs;

import com.example.bbs.dto.BBSCashPackageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BBSGateway {

    @Autowired
    private RestTemplate restTemplate;

    public void createBBSCashPackage(BBSCashPackageDTO dto) {
        restTemplate.postForObject("http://localhost:8080/bbs-package", dto, String.class);
    }
}
