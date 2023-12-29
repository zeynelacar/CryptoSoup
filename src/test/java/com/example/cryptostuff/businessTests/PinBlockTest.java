package com.example.cryptostuff.businessTests;

import com.example.cryptostuff.business.pinblock.PinBlock;
import com.example.cryptostuff.utils.Utilities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PinBlockTest {


    @InjectMocks
    Utilities utilities ;

    @InjectMocks
    PinBlock pinBlock ;

    String cardNo= "43219876543210987";
    String pan = "987654321098";
    String clearPin = "1234";

    @Test
    void generateClearZeroPinBlock() {
        assertEquals("0412AC89ABCDEF67", pinBlock.generateFormatZeroClearPinBlock(pan, clearPin));
    }

    @Test
    void openClearZeroPinBlock() {
        String clearPinBlock = "0412AC89ABCDEF67";
        assertEquals(pinBlock.decodePinBlockWithCard(cardNo,clearPinBlock),clearPin);
    }

    @Test
    void generateClearOnePinBlock() {
        String pbf_1 = pinBlock.generateFormatOneClearPinBlock(clearPin);
        assertEquals(pinBlock.decodePinBlockWithNoCard(pbf_1),clearPin);
    }

    @Test
    void generateClearTwoPinBlock() {
        String pbf_2 = pinBlock.generateFormatTwoPinBlock(clearPin);
        assertEquals(pinBlock.decodePinBlockWithNoCard(pbf_2),clearPin);
    }

    @Test
    void generateClearThreePinBlock() {
        String pbf_3 = pinBlock.generateFormatThreePinBlock(pan,clearPin);
        assertEquals(pinBlock.decodePinBlockWithCard(cardNo,pbf_3),clearPin);
    }

    @Test
    void generateClearFourPinBlock() {
        String pbf_4 = pinBlock.generateFormatFourClearPinBlock(clearPin);
        System.out.println(pbf_4);
        assertEquals(pinBlock.decodePinBlockWithNoCard(pbf_4), clearPin);
    }

    @Test
    void generateClearFourPanBlock() {
        String panBlock = pinBlock.generateFormatFourClearPanBlock(cardNo);
        assertEquals("54321987654321098700000000000000", panBlock);
    }

}
