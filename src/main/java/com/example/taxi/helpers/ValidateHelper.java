package com.example.taxi.helpers;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateHelper {

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^\\d{11}$");


    public static boolean validatePhoneNumber (String phoneNumber) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneNumber);

        return (matcher.find());
    }
}