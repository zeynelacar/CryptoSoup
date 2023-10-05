package com.example.cryptostuff.business.key;

import com.example.cryptostuff.dto.Generate3DESKeyDTO;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@Component
public class TripleDesKeyGenerator {



    public String generate(Short lengthType) throws NoSuchAlgorithmException {
        int bitSize = lengthType == 1 ? 168 : 112;
        String rawKey = baseAction(bitSize);
        return formatKey(rawKey,bitSize);
    }

    private String baseAction(Integer bitSize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
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
