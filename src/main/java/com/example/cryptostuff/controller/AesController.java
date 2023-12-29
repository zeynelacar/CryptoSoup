package com.example.cryptostuff.controller;

import com.example.cryptostuff.dto.EncryptDecryptAesDTO;
import com.example.cryptostuff.service.AesService;
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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/aes")
public class AesController {

    private final AesService aesService;

    @PostMapping(value = "/decrypt" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> decrypt(@Valid @RequestBody EncryptDecryptAesDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new ResponseEntity<>(aesService.encryptDecryptAes(request,0), HttpStatus.OK);
    }

    @PostMapping(value = "/encrypt" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> encrypt(@Valid @RequestBody EncryptDecryptAesDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new ResponseEntity<>(aesService.encryptDecryptAes(request,1), HttpStatus.OK);
    }
}
