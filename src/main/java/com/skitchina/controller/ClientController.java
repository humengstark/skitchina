package com.skitchina.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skitchina.mapper.ClientMapper;
import com.skitchina.model.Client;
import com.skitchina.model.ReturnResult;
import com.skitchina.utils.EncipherData;
import com.skitchina.utils.ReturnResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/13.
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController {

    private String key = "0000000000000000";

    @Autowired
    @Qualifier("clientMapperImpl")
    private ClientMapper clientMapper;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addClient", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String addClient(HttpServletRequest request) throws Exception {
        String str = request.getParameter("str");
        String json = EncipherData.Decrypt(str, key);
        JSONObject jsonObject = JSON.parseObject(json);

        String cellphone = jsonObject.getString("cellphone");
        String password = jsonObject.getString("password");
        String company_name = jsonObject.getString("company_name");
        String company_address = jsonObject.getString("company_address");
        String company_tel = jsonObject.getString("compamy_tel");
        String name = jsonObject.getString("name");

        Map params = new HashMap();

        params.put("cellphone", cellphone);
        params.put("password", password);
        params.put("company_name", company_name);
        params.put("company_address", company_address);
        params.put("company_tel", company_tel);
        params.put("name", name);

        clientMapper.addClient(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData("");

        return EncipherData.Encrypt(ReturnResultUtil.ReturnResultToJSON(returnResult),key);
    }
    /**
     * 用户登陆
     * @param request
     * @return
     * 0：登陆成功
     * 1：密码错误
     * 2：账号不存在
     */
    @ResponseBody
    @RequestMapping(value = "/clientLogin", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String clientLogin(HttpServletRequest request) throws Exception {
        String str = request.getParameter("str");
        String json = EncipherData.Decrypt(str, key);
        JSONObject jsonObject = JSON.parseObject(json);

        String cellphone = jsonObject.getString("cellphone");
        String password = jsonObject.getString("password");

        ReturnResult returnResult = new ReturnResult();
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData("");

        Client client = clientMapper.getClientByCellphone(cellphone);
        if (client != null) {
            if (client.getPassword().equals(password)) {
                returnResult.setCode(0);
            } else {
                returnResult.setCode(1);
            }
        } else {
            returnResult.setCode(2);
        }

        return EncipherData.Encrypt(ReturnResultUtil.ReturnResultToJSON(returnResult), key);
    }
}
