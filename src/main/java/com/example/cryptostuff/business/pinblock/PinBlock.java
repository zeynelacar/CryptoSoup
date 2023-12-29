package com.example.cryptostuff.business.pinblock;

import com.example.cryptostuff.constants.PinFormats;
import com.example.cryptostuff.utils.Utilities;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HexFormat;


@Component
public class PinBlock {

    static String[] supportedFormats = {PinFormats.ISO_0,
            PinFormats.ISO_1,
            PinFormats.ISO_2,
            PinFormats.ISO_3,
            PinFormats.ISO_4};

    public String generateFormatZeroClearPinBlock(String pan, String clearPin) {
        String paddedPan = Utilities.leftPad(pan, 16, '0');
        String sb = '0' +
                Integer.toHexString(clearPin.length()) +
                clearPin;
        String pinInput = Utilities.rightPad(sb, 16, 'F');
        byte[] clearPbBytes = Utilities.xorByteArrays(HexFormat.of().parseHex(paddedPan), HexFormat.of().parseHex(pinInput));
        return HexFormat.of().formatHex(clearPbBytes).toUpperCase();
    }

    public String generateFormatOneClearPinBlock(String clearPin) {
        String pb = '1' +
                Integer.toHexString(clearPin.length()) +
                clearPin;
        return Utilities.padRandomHexChars(pb, 16).toUpperCase();
    }

    public String generateFormatTwoPinBlock(String clearPin) {
        String pb = '2' +
                Integer.toHexString(clearPin.length()) +
                clearPin;
        return Utilities.rightPad(pb, 16, 'F').toUpperCase();
    }

    public String generateFormatThreePinBlock(String pan, String clearPin) {
        String paddedPan = Utilities.leftPad(pan, 16, '0');
        String sb = '3' +
                Integer.toHexString(clearPin.length()) +
                clearPin;
        String pinInput = Utilities.padRandomHexChars(sb, 16);
        byte[] clearPbBytes = Utilities.xorByteArrays(HexFormat.of().parseHex(paddedPan), HexFormat.of().parseHex(pinInput));
        return HexFormat.of().formatHex(clearPbBytes).toUpperCase();
    }

    public String generateFormatFourClearPinBlock(String clearPin) {
        String paddedPin = '4' +
                Integer.toHexString(clearPin.length()) +
                clearPin;
        String pinFirstHalf = Utilities.rightPad(paddedPin, 16, 'A');
        return Utilities.padRandomHexChars(pinFirstHalf, 32);
    }

    public String generateFormatFourClearPanBlock(String cardNo) {
        Integer m = cardNo.length() - 12;
        return Utilities.rightPad((m + cardNo), 32, '0');
    }

    public String decodePinBlockWithCard(String cardNumber, String pinBlock) {
        String pan = Utilities.calculatePan(cardNumber);
        String paddedPan = Utilities.leftPad(pan, 16, '0');
        byte[] paddedPin = Utilities.xorByteArrays(HexFormat.of().parseHex(paddedPan), HexFormat.of().parseHex(pinBlock));
        String openPin = HexFormat.of().formatHex(paddedPin);
        int endIndex = Integer.parseInt(String.valueOf(openPin.charAt(1)), 16);
        return openPin.substring(2, 2 + endIndex);
    }

    public String decodePinBlockWithNoCard(String pinBlock) {
        int endIndex = Integer.parseInt(String.valueOf(pinBlock.charAt(1)), 16);
        return pinBlock.substring(2, 2 + endIndex);
    }

    public boolean checkSupportedFormats(String format) {
        return Arrays.asList(supportedFormats).contains(format);
    }

}
