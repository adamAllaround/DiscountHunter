package com.allaroundjava.price.extract;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexNumberFinder implements NumberFinder {
    private static final String CURRENCY_SYMBOL = "[(\\p{Sc})(eur)(pln)(usd)(dkk)(gbp)]";

    @Override
    public String findValidNumber(String numberString) {
        Pattern currencyPattern = Pattern.compile(CURRENCY_SYMBOL, Pattern.CASE_INSENSITIVE);
        Matcher matcher = currencyPattern.matcher(numberString);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Could not find currency symbol in string");
        }
        return matcher.replaceAll("").trim();
    }
}
