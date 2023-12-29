package com.example.cryptostuff.constants;

import java.util.AbstractMap;
import java.util.Map;

public class ResponseCodes {

    public static final String SUCCESS = "00";
    public static final String VERIFICATION_FAILED = "01";
    public static final String INVALID_DATA = "15";
    public static final String PIN_FORMAT_ERROR = "20";
    public static final String INVALID_ACCOUNT_NUMBER = "21";
    public static final String PIN_BLOCK_ERROR = "24";
    public static final String FORMAT_NOT_SUPPORTED = "69";

    public static final Map<String, String> descriptionMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, String>(FORMAT_NOT_SUPPORTED, "Pin Block Format Not Supported"),
            new AbstractMap.SimpleEntry<String, String>(VERIFICATION_FAILED, "Verification or key Parity Error"),
            new AbstractMap.SimpleEntry<String, String>(INVALID_DATA, "Invalid of insufficient data"),
            new AbstractMap.SimpleEntry<String, String>(PIN_FORMAT_ERROR, "Pin Length Shorter than 4 or longer than 12"),
            new AbstractMap.SimpleEntry<String, String>(INVALID_ACCOUNT_NUMBER, "Given account is not valid"),
            new AbstractMap.SimpleEntry<String, String>(PIN_BLOCK_ERROR, "Pin Block does not contain valid values")
    );

    public static String getDescription(String statusCode) {
        return descriptionMap.get(statusCode);
    }
}
