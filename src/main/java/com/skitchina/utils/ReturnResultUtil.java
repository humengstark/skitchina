package com.skitchina.utils;

import com.alibaba.fastjson.JSON;
import com.skitchina.model.ReturnResult;

/**
 * Created by hu meng on 2017/4/17.
 */

/**
 * ���ؿͻ������ݹ���
 */
public class ReturnResultUtil {
    public static String ReturnResultToJSON(ReturnResult returnResult) {
        return JSON.toJSONString(returnResult);
    }
}
