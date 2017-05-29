package com.skitchina.utils;

import com.alibaba.fastjson.JSON;
import com.skitchina.model.ReturnResult;

/**
 * Created by hu meng on 2017/4/17.
 */

/**
 * 返回客户端数据工具
 */
public class ReturnResultUtil {
    public static String ReturnResultToJSON(ReturnResult returnResult) {
        return JSON.toJSONString(returnResult);
    }
}
