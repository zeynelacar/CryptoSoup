package com.example.cryptostuff.business.des;

import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class DES {

    static String desNoPadding = "DES/CBC/NoPadding";

    public String operate(String data, String key, Integer indicator) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(desNoPadding);
        SecretKey secret = new SecretKeySpec(HexFormat.of().parseHex(key), "DES");
        IvParameterSpec iv = new IvParameterSpec(new byte[8]);
        if (Cipher.ENCRYPT_MODE == indicator) {
            cipher.init(Cipher.ENCRYPT_MODE, secret, iv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, secret, iv);
        }
        byte[] cipherBytes = cipher.doFinal(HexFormat.of().parseHex(data));
        return HexFormat.of().formatHex(cipherBytes);
    }

}
