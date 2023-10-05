package com.example.cryptostuff.businessTests;

import com.example.cryptostuff.business.key.TripleDesKeyGenerator;
import com.example.cryptostuff.dto.Generate3DESKeyDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class KeyTest {

    @InjectMocks
    private TripleDesKeyGenerator tripleDesKeyGenerator;



    @Test
    void generateDoubleLengthTripleDesKey() throws NoSuchAlgorithmException {
        String res1 = tripleDesKeyGenerator.generate((short)0);
        assertNotNull(res1);
        assertEquals(32, res1.length());
    }

    @Test
    void generateTripleLengthTripleDesKey() throws NoSuchAlgorithmException {
        String res2 = tripleDesKeyGenerator.generate((short)1);
        assertNotNull(res2);
        assertEquals(48, res2.length());
    }
}
