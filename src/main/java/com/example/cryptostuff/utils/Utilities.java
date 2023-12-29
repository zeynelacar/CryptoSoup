package com.example.cryptostuff.utils;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;


public final class Utilities {

    public static final String IS_ALL_DIGIT = "\\d+";

    public static byte[] xorByteArrays (byte[] array1, byte[] array2) {
        int length = Math.min(array1.length, array2.length);
        byte[] result = new byte[length];

        for (int i = 0; i < length; i++) {
            result[i] = (byte) (array1[i] ^ array2[i]);
        }
        return result;
    }

    public static String calculatePan (@NotNull String cardNo) {
        int start = Math.max(0, cardNo.length()-13);
        return cardNo.substring(start , cardNo.length()-1);
    }

    public static String leftPad (String toBePadded, int finalLength, Character padChar) {
        String format = "%" + finalLength + "s";
        return String.format(format,toBePadded).replace(' ',padChar);
    }

    public static String rightPad (String toBePadded, int finalLength, Character padChar) {
        String format = "%-" + finalLength + "s";
        return String.format(format,toBePadded).replace(' ',padChar);
    }

    public static boolean isValidPinLength(int pinBlockLength) {
        return (pinBlockLength < 13 && pinBlockLength > 3);
    }

    public static boolean isAllDigits(String digitStr){
        return digitStr.matches(IS_ALL_DIGIT);
    }

    public static String padRandomHexChars (String toBePadded, int finalLength) {
        StringBuilder sb = new StringBuilder();
        SecureRandom sr = new SecureRandom(toBePadded.getBytes());
        sb.append(toBePadded);
        while(sb.length() < finalLength){
            sb.append(Integer.toHexString(sr.nextInt(16)).toUpperCase());
        }
        return sb.toString();
    }

    public static boolean checkLuhn(String cardNumber){
        char[] cardNo = cardNumber.toCharArray();
        int sum = 0;
        for(int i = 0 ; i<cardNo.length-1;++i){
            int digit = cardNo[i] - '0';
            if(i % 2 == 0){
                digit *= 2;
            }
            sum += digit/10 + digit%10;
        }
        return ((cardNo[cardNo.length-1] - '0') == (10 - (sum % 10)));
    }
}
