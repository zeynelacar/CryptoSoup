package com.example.cryptostuff.businessTests;

import com.example.cryptostuff.business.key.implemetations.AesKeyGenerator;
import com.example.cryptostuff.business.key.implemetations.TripleDesKeyGenerator;
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

    @InjectMocks
    private AesKeyGenerator aesKeyGenerator;



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

    @Test
    void generateDoubleLengthAesKey() throws NoSuchAlgorithmException {
        String res1 = aesKeyGenerator.generate((short)1);
        assertNotNull(res1);
        assertEquals(48, res1.length());
    }

    @Test
    void generateTripleLengthAesKey() throws NoSuchAlgorithmException {
        String res2 = aesKeyGenerator.generate((short)2);
        assertNotNull(res2);
        assertEquals(64, res2.length());
    }

    @Test
    void generateSingleLengthAesKey() throws NoSuchAlgorithmException {
        String res3 = aesKeyGenerator.generate((short)0);
        assertNotNull(res3);
        assertEquals(32,res3.length());
    }
}
