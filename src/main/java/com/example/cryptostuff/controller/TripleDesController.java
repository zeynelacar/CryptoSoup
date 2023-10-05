package com.example.cryptostuff.controller;

import com.example.cryptostuff.dto.DecryptDesDto;
import com.example.cryptostuff.service.TripleDesService;
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
@RequestMapping("v1/tripledes")
public class TripleDesController {

    private final TripleDesService service;

    @PostMapping(value = "/decrypt" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> tripleDesDecrypt(@Valid @RequestBody DecryptDesDto request ) throws InvalidAlgorithmParameterException,
            NoSuchPaddingException, IllegalBlockSizeException, IOException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return new ResponseEntity<>(service.decrypt(request), HttpStatus.OK);
    }

}
