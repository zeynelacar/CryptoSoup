package com.example.cryptostuff.controller;


import com.example.cryptostuff.dto.DecryptDesDto;
import com.example.cryptostuff.dto.Generate3DESKeyDTO;
import com.example.cryptostuff.service.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("v1/key")
public class KeyController {

    private final KeyService keyService;


    @GetMapping(value = "/generate/tripledes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> generateTripleDesKey(@RequestBody Generate3DESKeyDTO req) throws NoSuchAlgorithmException {
        return new ResponseEntity<>(keyService.generate3DESKey(req), HttpStatus.OK);
    }
}
