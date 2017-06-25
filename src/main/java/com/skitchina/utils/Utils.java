package com.skitchina.utils;

import com.alibaba.fastjson.JSONObject;
import com.skitchina.model.ReturnResult;

import javax.servlet.http.HttpServletRequest;
import java.util.IdentityHashMap;
import java.util.regex.Pattern;

/**
 * Created by hu meng on 2017/4/29.
 */
public class Utils {

    //判断是否是数字
    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //生成App版本号
    public static String createAppVersion(String version) {
        StringBuffer version1 = new StringBuffer(version.trim());
        version1.deleteCharAt(1);
        version1.deleteCharAt(2);
        String version2 = new String(version1);
        Integer version3 = Integer.parseInt(version2)+1;
        StringBuffer version4 = new StringBuffer(version3.toString());
        System.out.println(version4);
        version4.insert(1, ".");
        version4.insert(3, ".");
        String version5 = new String(version4);
        return version5;
    }

    public static String getJsonString(HttpServletRequest request) throws Exception {
        String str = request.getParameter("str");
        String key = "0000000000000000";
        String str1 = str.replace(" ", "+");
        String str2 = EncipherData.Decrypt(str1, key);
        return str2;
    }

    public static String returnEncrypt(ReturnResult returnResult) throws Exception {
        String key = "0000000000000000";
        return EncipherData.Encrypt(ReturnResultUtil.ReturnResultToJSON(returnResult), key);
    }

}
