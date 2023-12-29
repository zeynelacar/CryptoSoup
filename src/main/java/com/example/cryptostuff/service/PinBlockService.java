package com.example.cryptostuff.service;

import com.example.cryptostuff.dto.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PinBlockService {

    public String createClearPinBlock(ClearPinBlockGenerateDTO request);

    public GenericResponse createEncryptedPinBlock(EncPinBlockRequestDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

    public String decodeClearPinBlock(ClearPinBlockDecodeDTO request);

    public GenericResponse decodeEncryptedPinBlock(EncPinBlockDecodeDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException;

    }
