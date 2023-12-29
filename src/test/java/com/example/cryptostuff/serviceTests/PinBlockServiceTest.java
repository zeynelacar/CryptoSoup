package com.example.cryptostuff.serviceTests;

import com.example.cryptostuff.business.aes.AES;
import com.example.cryptostuff.business.pinblock.PinBlock;
import com.example.cryptostuff.business.tripledes.TripleDes;
import com.example.cryptostuff.dto.ClearPinBlockGenerateDTO;
import com.example.cryptostuff.service.implementations.PinBlockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PinBlockServiceTest {

    @InjectMocks
    PinBlockServiceImpl pinBlockService;

    @Mock
    private PinBlock pinBlock;

    @Mock
    private AES aes;

    @Mock
    TripleDes tripleDes;

    @Test
    void generateOneFormatPinBlock() {
        String expected = "0412AC89ABCDEF67";
        ClearPinBlockGenerateDTO clearReq = new ClearPinBlockGenerateDTO("43219876543210987",
                "1234",
                "00");
        when(pinBlock.checkSupportedFormats("00")).thenReturn(true);
        when(pinBlock.generateFormatZeroClearPinBlock("987654321098",clearReq.getClearPin())).thenReturn(expected);

        String result = pinBlockService.createClearPinBlock(clearReq);
        assertEquals(expected,result);
    }
}
