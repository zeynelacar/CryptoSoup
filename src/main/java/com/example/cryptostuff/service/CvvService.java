package com.example.cryptostuff.service;

import com.example.cryptostuff.dto.CalculateCvvDTO;
import com.example.cryptostuff.dto.GenericResponse;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface CvvService {

    public GenericResponse calculateCvv(CalculateCvvDTO req) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException;

    }
