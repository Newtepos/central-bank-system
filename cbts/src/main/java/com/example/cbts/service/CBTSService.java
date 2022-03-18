package com.example.cbts.service;

import com.example.cbts.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CBTSService {

    @Autowired
    BankRepository bankRepository;
}
