package com.example.cryptostuff.controller;

import com.example.cryptostuff.dto.CalculateCvvDTO;
import com.example.cryptostuff.dto.EncryptDecryptAesDTO;
import com.example.cryptostuff.dto.GenericResponse;
import com.example.cryptostuff.service.CvvService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/cvv")
public class CvvController {

    private final CvvService cvvService;

    @PostMapping(value = "/generateCvv" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> create(@Valid @RequestBody CalculateCvvDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        return new ResponseEntity<>(cvvService.calculateCvv(request), HttpStatus.OK);
    }

}
