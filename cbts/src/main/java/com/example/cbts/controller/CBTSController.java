package com.example.cbts.controller;

import com.example.cbts.service.CBTSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CBTSController {

    @Autowired
    CBTSService cbtsService;
}
