package com.example.cryptostuff.service;

import com.example.cryptostuff.dto.Generate3DESKeyDTO;

import java.security.NoSuchAlgorithmException;

public interface KeyService {

    String generate3DESKey(Generate3DESKeyDTO tripleDesDTO) throws NoSuchAlgorithmException;

}
