package com.example.cryptostuff.business.tripledes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class TripleDes {

    public static final Logger logger = LogManager.getLogger();
    static  String desNoPadding = "DES/CBC/NoPadding";

    public String operate(String data,String initialVector,String key,Integer indicator,String mode) throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        byte[] iv = HexFormat.of().parseHex(initialVector);
        byte[] initIv = new byte[8];
        byte[] temp = new byte[8];
        if (mode.equals("ECB"))
                iv = initIv;
        System.arraycopy(iv,0,initIv,0,8);
        byte[] dataDump = HexFormat.of().parseHex(data);
        byte[] adjustedData = adjustDataSize(dataDump);
        int index = 0;
        byte[] res = new byte[adjustedData.length];

        while (index <adjustedData.length){
            System.arraycopy(adjustedData,index,temp,0,8);
            byte[] resultValue = encodeDecodeCBC(temp,key,iv,indicator);
            System.arraycopy(resultValue,0,res,index,resultValue.length);
            index += 8;
        }
        return HexFormat.of().formatHex(res);
    }
    public  byte[] encodeDecodeCBC(byte[] cipher,String key,byte[] iv,Integer indicator) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] initialKey = HexFormat.of().parseHex(key);
        IvParameterSpec ivParam = new IvParameterSpec(iv);
        List<Cipher> ciphers = initializeCiphers();
        List<SecretKeySpec> secrets = getDESSecrets(initialKey);

        if (indicator == 0) {
            ciphers.get(0).init(Cipher.DECRYPT_MODE, secrets.get(0), ivParam);
            ciphers.get(1).init(Cipher.ENCRYPT_MODE, secrets.get(1), ivParam);
            ciphers.get(2).init(Cipher.DECRYPT_MODE, secrets.get(0), ivParam);
        } else {
            ciphers.get(0).init(Cipher.ENCRYPT_MODE, secrets.get(0), ivParam);
            ciphers.get(1).init(Cipher.DECRYPT_MODE, secrets.get(1), ivParam);
            ciphers.get(2).init(Cipher.ENCRYPT_MODE, secrets.get(0), ivParam);
        }
        return ciphers.get(2).doFinal(ciphers.get(1).doFinal(ciphers.get(0).doFinal(cipher)));
    }
    public  List<SecretKeySpec> getDESSecrets(byte[] key){
        return Arrays.asList(new SecretKeySpec(Arrays.copyOfRange(key,0,8),"DES"),
                new SecretKeySpec(Arrays.copyOfRange(key,8,16),"DES"));
    }

    public  List<Cipher> initializeCiphers() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Arrays.asList(Cipher.getInstance(desNoPadding),
                Cipher.getInstance(desNoPadding),
                Cipher.getInstance(desNoPadding));
    }

    public  byte[] adjustDataSize(byte[] dump) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(dump,0, dump.length);
        if(dump.length % 8 != 0){
            int missingLength = 8 - (buffer.toByteArray().length % 8);
            byte[] missingBytes = new byte[missingLength];
            for (int i =0;i<missingLength;i++){
                missingBytes[i] = (byte) missingLength;
            }
            buffer.write(missingBytes);
        }
        return buffer.toByteArray();
    }
}
