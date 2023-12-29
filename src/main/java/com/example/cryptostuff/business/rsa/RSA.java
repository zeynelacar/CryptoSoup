package com.example.cryptostuff.business.rsa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;


@Component
public class RSA {

    /*This code is an implementation of RSA attack used to solve picoCTF RSA challenge*/

    public static final String BYTE_BASE = "256";
    public static final BigInteger BIG_INT_BASE = new BigInteger(BYTE_BASE);
    public static final Logger logger = LogManager.getLogger();

    public String crackRSAUnsafe(String cip, String number, Long modulus, String componentP, String componentQ) {
        BigInteger cipher = new BigInteger(cip);
        BigInteger numVal = new BigInteger(number);
        BigInteger exponent = BigInteger.valueOf(modulus);

        //From FactorDB
        BigInteger p = new BigInteger(componentP);
        BigInteger q = new BigInteger(componentQ);

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger d = exponent.modInverse(phi);
        BigInteger m = cipher.modPow(d, numVal);
        return new String(bigIntToByteArray(m));
    }


    public byte[] bigIntToByteArray(BigInteger value) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        double z = Double.parseDouble(value.toString());
        int p = (int) Math.floor(Math.log(z) / Math.log(Double.parseDouble(BYTE_BASE)));
        int j = 0;
        while (j <= p) {
            byte k = value.mod(BIG_INT_BASE).byteValue();
            value = value.divide(BIG_INT_BASE);
            buffer.write(k);
            j++;
        }
        return buffer.toByteArray();
    }

}
