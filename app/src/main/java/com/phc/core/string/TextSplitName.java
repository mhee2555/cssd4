package com.phc.core.string;

public class TextSplitName {
    public static String SplitName(String str){
        String line = "";
            char[]arr=str.toCharArray();
            for(int i=0;i<arr.length;i++) {
                if (Character.isWhitespace(arr[i])) {
                    line =str.substring(0,i);
                }
            }

        return line;
    }
}
