package com.example.cryptostuff.service.implementations;

import com.example.cryptostuff.business.rsa.RSA;
import com.example.cryptostuff.dto.DecryptRSAUnsafeDTO;
import com.example.cryptostuff.service.RSAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RSAServiceImpl implements RSAService {

    private final RSA rsa;

    public String crackUnsafeRSA(DecryptRSAUnsafeDTO decryptRSA) {
        return null;
    }

}
