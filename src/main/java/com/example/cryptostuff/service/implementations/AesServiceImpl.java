package com.example.cryptostuff.service.implementations;

import com.example.cryptostuff.business.aes.AES;
import com.example.cryptostuff.dto.EncryptDecryptAesDTO;
import com.example.cryptostuff.exception.UnsupportedModeException;
import com.example.cryptostuff.service.AesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AesServiceImpl implements AesService {

    private static final List<String> validModes = List.of("CBC", "EBC");

    private final AES aes;

    @Override
    public String encryptDecryptAes(EncryptDecryptAesDTO request, Integer indicator) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (!checkValidMode(request.getEncryptionMode())) {
            throw new UnsupportedModeException("Encryption mode not supported");
        }
        String iv = request.getInitialVector();
        if (iv.length() < 32) {
            iv = String.format("%16s", iv).replace(' ', '0');
        }
        return aes.operate(request.getPlainHexData(), iv, request.getSecretKey(), indicator);
    }

    private boolean checkValidMode(String padding) {
        return validModes.contains(padding);
    }
}
