package com.example.cryptostuff.business.aes;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;

@Component
public class AES {

    public String operate(String data, String initialVector, String key, Integer indicator) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        byte[] iv = HexFormat.of().parseHex(initialVector);
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        byte[] initialKey = HexFormat.of().parseHex(key);
        SecretKey secret = new SecretKeySpec(initialKey, "AES");
        IvParameterSpec ivParam = new IvParameterSpec(iv);
        if (indicator == Cipher.ENCRYPT_MODE) {
            cipher.init(indicator, secret, ivParam);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secret, ivParam);
        }
        byte[] cipherData = cipher.doFinal(HexFormat.of().parseHex(data));
        return HexFormat.of().formatHex(cipherData).toUpperCase();
    }


}
