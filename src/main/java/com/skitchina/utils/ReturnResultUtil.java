package com.skitchina.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.skitchina.model.ReturnResult;

/**
 * Created by hu meng on 2017/4/17.
 */

/**
 * 返回工具类
 */
public class ReturnResultUtil {
    public static String ReturnResultToJSON(ReturnResult returnResult) {
        //加上SerializerFeature.WriteMapNullValue 可以防止null值没有字段
        return JSON.toJSONString(returnResult, SerializerFeature.WriteMapNullValue);
    }
}
