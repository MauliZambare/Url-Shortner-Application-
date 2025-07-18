package com.urlshortener.util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern URL_PATTERN = Pattern.compile("^(http|https)://.*$");

    public static boolean isValidUrl(String url) {
        return URL_PATTERN.matcher(url).matches();
    }
}