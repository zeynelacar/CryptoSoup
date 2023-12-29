package com.example.cryptostuff.service.implementations;

import com.example.cryptostuff.business.des.DES;
import com.example.cryptostuff.business.tripledes.TripleDes;
import com.example.cryptostuff.constants.ResponseCodes;
import com.example.cryptostuff.dto.CalculateCvvDTO;
import com.example.cryptostuff.dto.GenericResponse;
import com.example.cryptostuff.service.CvvService;
import com.example.cryptostuff.utils.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class CvvServiceImpl implements CvvService {

    private final DES des;

    private final TripleDes tripleDes;

    public GenericResponse calculateCvv(CalculateCvvDTO request) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException {
        if(request.getCvvKey().length() != 32){
            return new GenericResponse(ResponseCodes.INVALID_DATA,
                    ResponseCodes.getDescription(ResponseCodes.INVALID_DATA));
        }
        if(!Utilities.checkLuhn(request.getCardNumber()))
            return new GenericResponse(ResponseCodes.INVALID_ACCOUNT_NUMBER,
                    ResponseCodes.getDescription(ResponseCodes.INVALID_ACCOUNT_NUMBER));
        String input = request.getCardNumber() +
                request.getExpiryDate() +
                request.getServiceCode() ;
        String preparedData = Utilities.rightPad(input,32,'0');
        String firstHalf = preparedData.substring(0,16);
        String secondHalf = preparedData.substring(16);
        String cvvKeyA = request.getCvvKey().substring(0,16);
        byte[] intermediateBlockA = HexFormat.of().parseHex(des.operate(firstHalf, cvvKeyA,1));
        byte[] intermediateBlockB = Utilities.xorByteArrays(intermediateBlockA,HexFormat.of().parseHex(secondHalf));
        String finalBlock = tripleDes.operate(HexFormat.of().formatHex(intermediateBlockB),"0000000000000000",request.getCvvKey(),1,"ECB");
        String digitStr = String.format("%03d",Long.valueOf(finalBlock.replaceAll("\\D","")));
        String cvv = digitStr.substring(0,3);
        return new GenericResponse(ResponseCodes.SUCCESS, cvv);
    }

}
