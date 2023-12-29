package com.example.cryptostuff.service.implementations;

import com.example.cryptostuff.business.aes.AES;
import com.example.cryptostuff.business.pinblock.PinBlock;
import com.example.cryptostuff.business.tripledes.TripleDes;
import com.example.cryptostuff.constants.PinFormats;
import com.example.cryptostuff.constants.ResponseCodes;
import com.example.cryptostuff.dto.*;
import com.example.cryptostuff.service.PinBlockService;
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
import java.util.Arrays;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class PinBlockServiceImpl implements PinBlockService {

    private final PinBlock pinBlock;
    private final TripleDes tripleDes;
    private final AES aes;

    private static final String AES_IV = "00000000000000000000000000000000";
    private static final String TDES_IV = "0000000000000000";

    public String createClearPinBlock(ClearPinBlockGenerateDTO genRequest){
        String format = genRequest.getPinBlockFormat();
        String pan = Utilities.calculatePan(genRequest.getCardNo());
        String pin = genRequest.getClearPin();
        String clearPinBlock;
        switch (format){
            case PinFormats.ISO_1 -> clearPinBlock = pinBlock.generateFormatOneClearPinBlock(pin);
            case PinFormats.ISO_2 -> clearPinBlock = pinBlock.generateFormatTwoPinBlock(pin);
            case PinFormats.ISO_3 -> clearPinBlock = pinBlock.generateFormatThreePinBlock(pan,pin);
            case PinFormats.ISO_4 -> clearPinBlock = pinBlock.generateFormatFourClearPinBlock(pin);
            default -> clearPinBlock = pinBlock.generateFormatZeroClearPinBlock(pan,pin);
        }
        return clearPinBlock;
    }

    public GenericResponse createEncryptedPinBlock(EncPinBlockRequestDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if(Boolean.FALSE.equals(pinBlock.checkSupportedFormats(request.getPinBlockFormat()))){
            return new GenericResponse(ResponseCodes.FORMAT_NOT_SUPPORTED,
                    ResponseCodes.getDescription(ResponseCodes.FORMAT_NOT_SUPPORTED));
        }
        ClearPinBlockGenerateDTO clearRequest = new ClearPinBlockGenerateDTO(request.getCardNo(),
                request.getClearPin(),
                request.getPinBlockFormat());

        String clearPinBlock = createClearPinBlock(clearRequest);

        String cipheredPinBlock;

        if(Boolean.FALSE.equals(request.getPinBlockFormat().equals(PinFormats.ISO_4))){
            cipheredPinBlock = tripleDes.operate(clearPinBlock,TDES_IV, request.getPinKey(), 1,"ECB");
        } else {
            String panBlock = pinBlock.generateFormatFourClearPanBlock(request.getCardNo());
            String intermediateBlock = aes.operate(clearPinBlock,AES_IV, request.getPinKey(), 1);
            byte[] intermediateArr = Utilities.xorByteArrays(HexFormat.of().parseHex(panBlock),HexFormat.of().parseHex(intermediateBlock));
            cipheredPinBlock = aes.operate(HexFormat.of().formatHex(intermediateArr),AES_IV, request.getPinKey(), 1);
        }
        return new GenericResponse(ResponseCodes.SUCCESS,cipheredPinBlock.toUpperCase());
    }

    public GenericResponse decodeEncryptedPinBlock(EncPinBlockDecodeDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        String format = request.getPinBlockFormat();
        if(Boolean.FALSE.equals(pinBlock.checkSupportedFormats(request.getPinBlockFormat()))){
            return new GenericResponse(ResponseCodes.FORMAT_NOT_SUPPORTED,
                    ResponseCodes.getDescription(ResponseCodes.FORMAT_NOT_SUPPORTED));
        }
        String clearPinBlock;
        if(PinFormats.ISO_4.equals(format)){
            clearPinBlock = decodeAesPinBlock(request.getCardNo(),
                    request.getPinKey(),
                    request.getPinBlock());
        }else {
            clearPinBlock = decodeTDesPinBlock(request.getPinKey(),
                    request.getPinBlock());
        }
        if (!Utilities.isValidPinLength(Integer.parseInt(String.valueOf(clearPinBlock.charAt(1)),16)))
            return new GenericResponse(ResponseCodes.PIN_FORMAT_ERROR,
                    ResponseCodes.getDescription(ResponseCodes.PIN_FORMAT_ERROR));
        ClearPinBlockDecodeDTO clear = new ClearPinBlockDecodeDTO(request.getCardNo(),
                clearPinBlock,
                request.getPinBlockFormat());

        String clearPin = decodeClearPinBlock(clear);
        GenericResponse response = checkForValidPin(clearPin);
        if(response.getStatusCode().equals(ResponseCodes.SUCCESS))
            response.setResponseData(clearPin);
        return response;

    }

    public String decodeClearPinBlock(ClearPinBlockDecodeDTO request){
        String format = request.getPinBlockFormat();
        String cardNo = request.getCardNo();
        String clearPin;
        if (Arrays.asList(PinFormats.ISO_1,PinFormats.ISO_2,PinFormats.ISO_4).contains(format)){
            clearPin = pinBlock.decodePinBlockWithNoCard(request.getPinBlock());
        } else {
            clearPin = pinBlock.decodePinBlockWithCard(cardNo,request.getPinBlock());
        }
        return clearPin;
    }

    public String decodeAesPinBlock(String cardNo,String key, String encPinBlock) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String intermediateBlockB = aes.operate(encPinBlock,AES_IV,key,0);
        byte[] intermediateArrayB = HexFormat.of().parseHex(intermediateBlockB);
        String panBlock = pinBlock.generateFormatFourClearPanBlock(cardNo);
        byte[] intermediateArrayA = Utilities.xorByteArrays(intermediateArrayB,HexFormat.of().parseHex(panBlock));
        return aes.operate(HexFormat.of().formatHex(intermediateArrayA),AES_IV,key,0);
    }

    public String decodeTDesPinBlock(String key,String encPinBlock) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return tripleDes.operate(encPinBlock,TDES_IV,key,0,"ECB");
    }

    private GenericResponse checkForValidPinBlock(String clearPinBlock){
        GenericResponse resp = new GenericResponse(ResponseCodes.SUCCESS,"Success");
        if(Utilities.isValidPinLength(Integer.parseInt(String.valueOf(clearPinBlock.charAt(1)),16))){
            resp.setStatusCode(ResponseCodes.PIN_FORMAT_ERROR);
            resp.setResponseData(ResponseCodes.getDescription(ResponseCodes.PIN_FORMAT_ERROR));
        }
        return resp;
    }

    private GenericResponse checkForValidPin(String clearPin){
        GenericResponse resp = new GenericResponse(ResponseCodes.SUCCESS,"Success");
        if(Boolean.FALSE.equals(Utilities.isAllDigits(clearPin))){
            resp.setStatusCode(ResponseCodes.PIN_BLOCK_ERROR);
            resp.setResponseData(ResponseCodes.getDescription(ResponseCodes.PIN_BLOCK_ERROR));
        }
        return resp;
    }

}
