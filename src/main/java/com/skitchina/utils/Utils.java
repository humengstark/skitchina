package com.skitchina.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.skitchina.model.ReturnResult;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;
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

    //解密请求
    public static String getJsonString(HttpServletRequest request) throws Exception {
        String key = "0000000000000000";
        String str = request.getParameter("str");
        System.out.println(str);
        String str1 = str.replace(" ", "+");
        String str2 = EncipherData.Decrypt(str1, key);
        return str2;
    }

    //测试
    public static String getJsonString2(String str) throws Exception {
        System.out.println(str);
        String key = "0000000000000000";
        String str1 = str.replace(" ", "+");
        String str2 = EncipherData.Decrypt(str1, key);
        return str2;
    }

    //加密返回结果
    public static String returnEncrypt(ReturnResult returnResult) throws Exception {
        String key = "0000000000000000";
        String result = EncipherData.Encrypt(ReturnResultUtil.ReturnResultToJSON(returnResult), key);
        Map map = new HashMap();
        map.put("str", result);
        return JSON.toJSONString(map);
    }

    //随机生成4位数字
    public static int getRandomNum() {
        int num = (int) (Math.random() * 9000+1000);
        return num;
    }

    public static String getKey() {
        Random ran=new Random();
        int a=ran.nextInt(99999999);
        int b=ran.nextInt(99999999);
        long l=a*100000000L+b;
        String num=String.valueOf(l);
        return num;
    }

}
