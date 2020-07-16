package com.phc.core.string;

public class TwoZero {
    public static String get(String data) {
        String dd = data;
        if (data.length() < 2) dd = "0" + data;
        return dd;
    }
}
