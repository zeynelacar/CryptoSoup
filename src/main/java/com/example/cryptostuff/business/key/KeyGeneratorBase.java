package com.example.cryptostuff.business.key;


import java.security.NoSuchAlgorithmException;

public interface KeyGeneratorBase {

    String generate(Short lengthType) throws NoSuchAlgorithmException;



}
