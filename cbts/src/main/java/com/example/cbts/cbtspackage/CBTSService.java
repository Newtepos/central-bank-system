package com.example.cbts.cbtspackage;

import com.example.cbts.bbspackage.BBSGateway;
import com.example.cbts.dto.CBTSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.DispatchActionRequest;
import com.example.cbts.dto.QRCodeDTO;
import com.example.cbts.entites.CBTSCashPackage;
import com.example.cbts.qrcode.QRCodeGenarator;
import com.example.cbts.repository.CBTSCashPackageRepository;
import com.example.cbts.service.CoreBankingService;
import com.example.cbts.service.UtilityService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CBTSService {

    @Autowired
    UtilityService utilityService;

    @Autowired
    CBTSCashPackageRepository cbtsCashPackageRepository;

    @Autowired
    CoreBankingService coreBankingService;

    @Autowired
    BBSGateway bbsGateway;

    @Autowired
    QRCodeGenarator qrCodeGenarator;

    public void createCBTSCashPackage(CBTSCashPackageDTO dto) {
        //validate input
        utilityService.bankNotFound(dto.getBranchId());
        utilityService.validateCenterBankFund(dto.getCurrency(), dto.getAmount());

        UUID cashPackageID = UUID.randomUUID();
        dto.setPackageId(cashPackageID);
        CBTSCashPackage cashPackage = utilityService.covertCBTSCashPackageDtoToEntity(dto);
        cbtsCashPackageRepository.save(cashPackage);

        //Update CashPackage to BB System
        bbsGateway.createCBTSCashPackage(dto);
    }

    public void updateCBTSStatus(DispatchActionRequest dto, UUID packageId) {
        //validate CBTSPackage
        utilityService.validateCBTSCashPackage(packageId);

        CBTSCashPackage queryResult = cbtsCashPackageRepository.getByPackageId(packageId);

        //dispatch action
        if(dto.getMethod().equals("sent")) {
            queryResult.setSendStatus(true);
            queryResult.setSendTime(dto.getActionTime());

            //update Central Balance
            coreBankingService.decreaseCentralBankBalance(queryResult);
        }
        else if(dto.getMethod().equals("received")) {
            queryResult.setReceiveStatus(true);
            queryResult.setReceivedTime(dto.getActionTime());
        }
        cbtsCashPackageRepository.save(queryResult);
    }

    public List<CBTSCashPackageDTO> getAllCBTSCashPackage() {
        List<CBTSCashPackageDTO> cbtsCashPackageDTOS = new ArrayList<>();
        List<CBTSCashPackage> queryResult = cbtsCashPackageRepository.findAll();
        for(CBTSCashPackage cashPackage: queryResult) {
            cbtsCashPackageDTOS.add(utilityService.covertCBTSCashPackageEntityToDto(cashPackage));
        }

        return cbtsCashPackageDTOS;
    }

    public CashDTO readCBTSPackage(UUID packageId) {
        CashDTO cashDTO = new CashDTO();
        CBTSCashPackage query = cbtsCashPackageRepository.getByPackageId(packageId);
        cashDTO.setAmount(query.getCash().getAmount());
        cashDTO.setCurrency(query.getCash().getCurrency().getCurrency());
        return cashDTO;
    }

    public QRCodeDTO createCBTSQRCODE(UUID packageId) {

        QRCodeDTO qrCodeDTO = new QRCodeDTO();
        String cbts_url = "http://localhost:8080/cbts-package/" + packageId + "/qr-code/";
        try {
            String send_qrcode = qrCodeGenarator.getQRCodeImage(cbts_url + "send/true", 250 , 250);
            String receive_qrcode = qrCodeGenarator.getQRCodeImage(cbts_url + "receive/true", 250, 250);

            qrCodeDTO.setSendQRCodeBase64(send_qrcode);
            qrCodeDTO.setReceiveQRCodeBase64(receive_qrcode);
        }
        catch (WriterException | IOException e) {
            e.printStackTrace();
        }

        return qrCodeDTO;
    }


}
