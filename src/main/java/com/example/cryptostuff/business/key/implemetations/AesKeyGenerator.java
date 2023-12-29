package com.example.cryptostuff.business.key.implemetations;

import com.example.cryptostuff.business.key.KeyGeneratorBase;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class AesKeyGenerator implements KeyGeneratorBase {

    public static final String AES = "AES";

    @Override
    public String generate(Short lengthType) throws NoSuchAlgorithmException {
        int bitSize;
        switch (lengthType) {
            case 2 -> bitSize = 256;
            case 1 -> bitSize = 192;
            default -> bitSize = 128;
        }
        String rawKey = baseAction(bitSize);
        return formatKey(rawKey, bitSize);
    }

    private String baseAction(Integer bitSize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(bitSize);
        SecretKey key = keyGenerator.generateKey();
        return HexFormat.of().formatHex(key.getEncoded());
    }

    private String formatKey(String key, Integer bitSize) {
        String formattedKey = key.toUpperCase();
        if (bitSize == 192)
            formattedKey = formattedKey.substring(0, 48);
        return formattedKey;

    }
}
