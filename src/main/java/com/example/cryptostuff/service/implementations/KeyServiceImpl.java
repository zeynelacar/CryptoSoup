package com.example.cryptostuff.service.implementations;

import com.example.cryptostuff.business.key.implemetations.TripleDesKeyGenerator;
import com.example.cryptostuff.dto.Generate3DESKeyDTO;
import com.example.cryptostuff.service.KeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class KeyServiceImpl implements KeyService {

    private final TripleDesKeyGenerator tripleDESGenerator;

    @Override
    public String generate3DESKey(Generate3DESKeyDTO tripleDesDTO) throws NoSuchAlgorithmException {
        return tripleDESGenerator.generate(tripleDesDTO.getLengthType());
    }
}
