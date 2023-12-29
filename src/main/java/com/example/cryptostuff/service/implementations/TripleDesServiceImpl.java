package com.example.cryptostuff.service.implementations;

import com.example.cryptostuff.business.tripledes.TripleDes;
import com.example.cryptostuff.dto.DecryptDesDto;
import com.example.cryptostuff.exception.UnsupportedModeException;
import com.example.cryptostuff.service.TripleDesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TripleDesServiceImpl implements TripleDesService {

    private static final List<String> validModes = List.of("CBC", "EBC");
    private final TripleDes tripleDes;

    @Override
    public String decrypt(DecryptDesDto req) throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (!checkValidPadding(req.getPaddingMode())) {
            throw new UnsupportedModeException("Encryption mode not supported");
        }
        String iv = req.getInitialVector();
        if (iv.length() < 16) {
            iv = String.format("%16s", iv).replace(' ', '0');
        }
        return tripleDes.operate(req.getPlainTextData(), iv, req.getSecretKey(), 0, req.getPaddingMode());
    }

    private boolean checkValidPadding(String padding) {
        return validModes.contains(padding);
    }
}
