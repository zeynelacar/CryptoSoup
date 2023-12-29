package com.example.cryptostuff.controller;

import com.example.cryptostuff.dto.EncPinBlockDecodeDTO;
import com.example.cryptostuff.dto.EncPinBlockRequestDTO;
import com.example.cryptostuff.dto.EncryptDecryptAesDTO;
import com.example.cryptostuff.dto.GenericResponse;
import com.example.cryptostuff.service.PinBlockService;
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
@RequestMapping("v1/pinBlock")
public class PinBlockController {

    private final PinBlockService pinBlockService;

    @PostMapping(value = "/decrypt", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> decrypt(@Valid @RequestBody EncPinBlockDecodeDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        return new ResponseEntity<>(pinBlockService.decodeEncryptedPinBlock(request), HttpStatus.OK);
    }

    @PostMapping(value = "/encrypt", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> encrypt(@Valid @RequestBody EncPinBlockRequestDTO request) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, IOException {
        return new ResponseEntity<>(pinBlockService.createEncryptedPinBlock(request), HttpStatus.OK);
    }

}
