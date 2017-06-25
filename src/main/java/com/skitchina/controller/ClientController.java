package com.skitchina.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skitchina.mapper.ClientMapper;
import com.skitchina.mapper.ClientWaybillMapper;
import com.skitchina.mapper.WaybillMapper;
import com.skitchina.model.Client;
import com.skitchina.model.ReturnResult;
import com.skitchina.model.Waybill;
import com.skitchina.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    @Qualifier("clientWaybillMapperImpl")
    private ClientWaybillMapper clientWaybillMapper;

    @Autowired
    @Qualifier("waybillMapperImpl")
    private WaybillMapper waybillMapper;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addClient", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String addClient(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        String cellphone = jsonObject.getString("cellphone").trim();
        String password = jsonObject.getString("password").trim();
        String company_name = jsonObject.getString("company_name").trim();
        String company_address = jsonObject.getString("company_address").trim();
        String company_tel = jsonObject.getString("company_tel").trim();
        String name = jsonObject.getString("name").trim();

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

        return Utils.returnEncrypt(returnResult);
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
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        String cellphone = jsonObject.getString("cellphone").trim();
        String password = jsonObject.getString("password").trim();

        ReturnResult returnResult = new ReturnResult();
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData("");

        Client client = clientMapper.getClientByCellphone(cellphone);
        if (client != null) {
            if (client.getPassword().equals(password)) {
                returnResult.setCode(0);
                returnResult.setData(client);
            } else {
                returnResult.setCode(1);
            }
        } else {
            returnResult.setCode(2);
        }
        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 用户下单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addClientWaybill", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String clientAddWaybill(HttpServletRequest request) throws Exception {

        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("client_id", Integer.parseInt(jsonObject.getString("client_id").trim()));
        params.put("time1", time);
        params.put("origin", jsonObject.getString("origin").trim());
        params.put("destination", jsonObject.getString("destination").trim());
        params.put("consignor_tel", jsonObject.getString("consignor_tel").trim());
        params.put("consignee_tel", jsonObject.getString("consignee_tel").trim());
        params.put("consignor_company", jsonObject.getString("consignor_company").trim());
        params.put("consignee_company", jsonObject.getString("consignee_company").trim());
        params.put("consignor_address", jsonObject.getString("consignor_address").trim());
        params.put("consignee_address", jsonObject.getString("consignee_address").trim());
        params.put("price", Double.parseDouble(jsonObject.getString("price").trim()));
        params.put("num", Integer.parseInt(jsonObject.getString("num").trim()));
        params.put("payway", Integer.parseInt(jsonObject.getString("payway").trim()));
        params.put("remark", jsonObject.getString("remark").trim());

        clientWaybillMapper.addClientWaybill(params);

        ReturnResult returnResult = new ReturnResult(0);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 根据waybill_id查询waybill
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaybillByWaybillId")
    public String getWaybillByWaybillId(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int waybill_id = Integer.parseInt(jsonObject.getString("waybill_id"));

        Waybill waybill = waybillMapper.getWaybillByWaybill_id(waybill_id);

        ReturnResult returnResult = new ReturnResult(0, 0, "", waybill);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 选择对账时间
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCheckTime")
    public String updateCheckTime(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int id = Integer.parseInt(jsonObject.getString("id"));
        int checkTime = Integer.parseInt(jsonObject.getString("checkTime"));

        Map params = new HashMap();
        params.put("id", id);
        params.put("checkTime", checkTime);

        clientMapper.updateCheckTime(params);

        ReturnResult returnResult = new ReturnResult(0);

        return Utils.returnEncrypt(returnResult);
    }
}
