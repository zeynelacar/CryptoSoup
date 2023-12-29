package com.example.cryptostuff.business.key.implemetations;

import com.example.cryptostuff.business.key.KeyGeneratorBase;
import com.example.cryptostuff.dto.Generate3DESKeyDTO;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class TripleDesKeyGenerator implements KeyGeneratorBase {

    public static final String TRIPLE_DES = "DESede";

    @Override
    public String generate(Short lengthType) throws NoSuchAlgorithmException {
        int bitSize = lengthType == 1 ? 168 : 112;
        String rawKey = baseAction(bitSize);
        return formatKey(rawKey,bitSize);
    }

    private String baseAction(Integer bitSize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(TRIPLE_DES);
        keyGenerator.init(bitSize);
        SecretKey key = keyGenerator.generateKey();
        return HexFormat.of().formatHex(key.getEncoded());
    }

    private String formatKey(String key,Integer bitSize){
        String formattedKey = key.toUpperCase();
        if (bitSize == 112)
            formattedKey = formattedKey.substring(0,32);
        return formattedKey;

    }

}
