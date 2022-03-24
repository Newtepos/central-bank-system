package com.example.bbs.cbtspackage;

import com.example.bbs.dto.CBTSCashPackageDTO;
import com.example.bbs.repository.CBTSCashPackageRepository;
import com.example.bbs.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CBTSPackageService {

    @Autowired
    CBTSCashPackageRepository cbtsCashPackageRepository;

    @Autowired
    UtilityService utilityService;

    public void createCBTSCashPackage(CBTSCashPackageDTO dto) {
        cbtsCashPackageRepository.save(utilityService.covertCBTSCashPackageDtoToEntity(dto));
    }
}
