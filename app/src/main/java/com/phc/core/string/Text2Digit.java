package com.phc.core.string;

public class Text2Digit {
    public static String twoDigit(String txt){
        return txt.length() == 1 ? "0"+txt : txt ;
    }
}
