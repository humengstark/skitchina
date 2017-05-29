package com.skitchina.utils;

import java.util.regex.Pattern;

/**
 * Created by hu meng on 2017/4/29.
 */
public class Utils {

    //ÅĞ¶ÏÊÇ·ñÎªÊı×Ö
    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
