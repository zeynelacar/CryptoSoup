package com.example.cryptostuff.businessTests;

import com.example.cryptostuff.business.aes.AES;
import com.example.cryptostuff.utils.Utilities;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AesTest {

    @InjectMocks
    private AES aes;

    String data ="6B20BD9A4C52D1D00E1FFD081AA841525396F4C7854D80A7171C5AB01D6F0055";
    String key = "6B20BD9A4C52D1D00E1FFD081AA841525396F4C7854D80A7171C5AB01D6F0055";
    String iv = "00000000000000000000000000000000";
    String cipherData = "8CE42922BF54FEB22EC604B8817362156B965818C2335AA6D92892C167E7D8E3";

    String clearPB = "441234AAAAAAAAAAC634DC97DC8CB525";

    String panBlock = "54321987654321098700000000000000";

    String aes192key = "E8609E7E3F71ECAE05136C685032FFCE9E3E394ED11EF1F8";

    String aes128Key = "07E72F9ACC5B006E0F93D738E4043084";

    @Test
    void encrypt() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, IOException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        assertEquals(aes.operate(data,iv,key,1),cipherData);
    }

    @Test
    void decrypt() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        assertEquals(aes.operate(cipherData,iv,key,0),data);
    }

    @Test
    void encryptAesPinBlock() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        assertEquals("D06788CE0C9EB28C52728B28447669CD",getAesPinBlock());
    }

    @Test
    void finalizeAes128PinBlock() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String intermediateBlockA = getAesPinBlock();
        byte[] intermediateBlockB = Utilities.xorByteArrays(HexFormat.of().parseHex(intermediateBlockA),
                HexFormat.of().parseHex(panBlock));
        String finalBlock = aes.operate(HexFormat.of().formatHex(intermediateBlockB),iv,aes128Key,1);
        assertEquals("80CB22DA8709300AB9D92F2D09A9F57C",finalBlock);
    }

    @Test
    void finalizeAes192PinBlock() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String intermediateBlockA = getAesPinBlock();
        byte[] intermediateBlockB = Utilities.xorByteArrays(HexFormat.of().parseHex(intermediateBlockA),
                HexFormat.of().parseHex(panBlock));
        String finalBlock = aes.operate(HexFormat.of().formatHex(intermediateBlockB),iv,aes192key,1);
        assertEquals("FA719E18055A3F80201A194892BF768A",finalBlock);
    }

    @Test
    void decryptAesPinBlock() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String intermediateBlockA = "D06788CE0C9EB28C52728B28447669CD";
        String clearPinBlock = aes.operate(intermediateBlockA,iv,aes128Key,0);
        assertEquals(clearPB, clearPinBlock);
    }

    @Test
    void decryptFinalizedAesPinBlock() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        String finalPinBlock = "80CB22DA8709300AB9D92F2D09A9F57C";
        String intermediateBlockB = aes.operate(finalPinBlock,iv,aes128Key,0);
        byte[] interArr = Utilities.xorByteArrays(HexFormat.of().parseHex(intermediateBlockB),HexFormat.of().parseHex(panBlock));
        String openPinBlock = aes.operate(HexFormat.of().formatHex(interArr),iv,aes128Key,0);
        assertEquals(clearPB,openPinBlock);
    }

    String getAesPinBlock() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return aes.operate(clearPB,iv,aes128Key,1);
    }



}
