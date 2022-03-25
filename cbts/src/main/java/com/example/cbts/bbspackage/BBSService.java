package com.example.cbts.bbspackage;

import com.example.cbts.dto.QRCodeDTO;
import com.example.cbts.qrcode.QRCodeGenarator;
import com.example.cbts.service.UtilityService;
import com.example.cbts.dto.BBSCashPackageDTO;
import com.example.cbts.dto.CashDTO;
import com.example.cbts.dto.DispatchActionRequest;
import com.example.cbts.entites.BBSCashPackage;
import com.example.cbts.repository.BBSCashPackageRepository;
import com.example.cbts.service.CoreBankingService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BBSService {

    @Autowired
    BBSCashPackageRepository bbsCashPackageRepository;

    @Autowired
    UtilityService utilityService;

    @Autowired
    BBSGateway bbsGateway;

    @Autowired
    CoreBankingService coreBankingService;

    @Autowired
    QRCodeGenarator qrCodeGenarator;

    public CashDTO readBBSPackage(UUID uuid) {
        return bbsGateway.readBBSPackage(uuid);
    }

    public List<BBSCashPackageDTO> getAllBBSCashPackage() {
        List<BBSCashPackageDTO> bbsCashPackageDTOS = new ArrayList<>();
        List<BBSCashPackage> queryResult = bbsCashPackageRepository.findAll();
        for(BBSCashPackage cashPackage: queryResult) {
            bbsCashPackageDTOS.add(utilityService.covertBBSCashPackageEntityToDto(cashPackage));
        }

        return bbsCashPackageDTOS;
    }

    public void createBBSCashPackage(BBSCashPackageDTO dto) {
        //validate input
        utilityService.bankNotFound(dto.getBranchId());

        BBSCashPackage cashPackage = utilityService.covertBBSCashPackageDtoToEntity(dto);
        bbsCashPackageRepository.save(cashPackage);
    }

    public void updateBBSStatus(DispatchActionRequest dto, UUID packageId) {
        //validate CBTSPackage
        utilityService.validateBBSCashPackage(packageId);

        BBSCashPackage queryResult = bbsCashPackageRepository.getByPackageId(packageId);

        //dispatch action
        if(dto.getMethod().equals("sent")) {
            queryResult.setSendStatus(true);
            queryResult.setSendTime(dto.getActionTime());
        }

        else if(dto.getMethod().equals("received")) {
            queryResult.setReceiveStatus(true);
            queryResult.setReceivedTime(dto.getActionTime());

            //update Central Balance
            coreBankingService.increaseCentralBankBalance(queryResult);
        }
        bbsCashPackageRepository.save(queryResult);
    }

    public QRCodeDTO createBBSQRCODE(UUID packageId) {

        QRCodeDTO qrCodeDTO = new QRCodeDTO();
        String cbts_url = "http://localhost:8080/bbs-package/" + packageId + "/qr-code/";
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
