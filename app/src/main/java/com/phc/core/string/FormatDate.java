package com.phc.core.string;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class FormatDate {
    public static String dateString(String date)
    {
        String reDate="";
         date = date.replace("-", "/");
            String[] xdate = date.split("/");
            xdate[xdate.length-1] = (Integer.parseInt(xdate[xdate.length-1])-1957)+"";
        reDate=xdate[0]+"/"+xdate[1]+"/"+xdate[2];
        return reDate;
    }
}
