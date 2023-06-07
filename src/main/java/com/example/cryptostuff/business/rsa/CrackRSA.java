package com.example.cryptostuff.business.rsa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

public class CrackRSA {

    /*This code is an implementation of RSA attack used to solve picoCTF RSA challenge*/

    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {

        BigInteger cipher = new BigInteger("62324783949134119159408816513334912534343517300880137691662780895409992760262021");
        BigInteger numVal = new BigInteger("1280678415822214057864524798453297819181910621573945477544758171055968245116423923");
        BigInteger exponent = BigInteger.valueOf(65537L);

        //From FactorDB
        BigInteger p = new BigInteger("1899107986527483535344517113948531328331");
        BigInteger q = new BigInteger("674357869540600933870145899564746495319033");

        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        BigInteger d = exponent.modInverse(phi);
        BigInteger m = cipher.modPow(d,numVal);

        String decodedValue = decodeRSA(m);
        logger.info("Here is the flag : {}", decodedValue);

    }

    public static final String BYTE_BASE = "256";

    static String decodeRSA(BigInteger cipherText){
        return decodedASCII(getBytes(cipherText));
    }

    static byte[] getBytes (BigInteger value) {
        BigInteger base = new BigInteger(BYTE_BASE);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        double z = Double.parseDouble(value.toString());
        int p = (int) Math.floor(Math.log(z)/Math.log(Double.parseDouble(BYTE_BASE)));
        int j = 0;
        while (j<=p){
            byte k=value.mod(base).byteValue();
            value=value.divide(base);
            buffer.write(k);
            j++;
        }
        return buffer.toByteArray();
    }

    static String decodedASCII (byte[] ascii) {
        StringBuilder ascii256= new StringBuilder();
        String g;
        for (Byte b : ascii) {
            ascii256.append((char) (b & 0xff));
        }
        g = ascii256.toString().startsWith("pico") ? ascii256.toString() : ascii256.reverse().toString();
        return g;
    }
}
