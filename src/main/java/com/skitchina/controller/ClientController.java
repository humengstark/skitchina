package com.skitchina.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skitchina.mapper.*;
import com.skitchina.model.Client;
import com.skitchina.model.Notice;
import com.skitchina.model.ReturnResult;
import com.skitchina.model.Waybill;
import com.skitchina.utils.ReturnResultUtil;
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
import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/13.
 */
@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    @Qualifier("clientMapperImpl")
    private ClientMapper clientMapper;

    @Autowired
    @Qualifier("clientWaybillMapperImpl")
    private ClientWaybillMapper clientWaybillMapper;

    @Autowired
    @Qualifier("waybillMapperImpl")
    private WaybillMapper waybillMapper;

    @Autowired
    @Qualifier("drawMoneyMapperImpl")
    private DrawMoneyMapper drawMoneyMapper;

    @Autowired
    @Qualifier("noticeMapperImpl")
    private NoticeMapper noticeMapper;

    /**
     * 获取key
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getKey", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getKey() {
        String key = Utils.getKey();
        ReturnResult returnResult = new ReturnResult(0, 0, "", key);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 用户注册
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addClient", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String addClient(HttpServletRequest request) throws Exception {
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
    public synchronized String clientAddWaybill(HttpServletRequest request) throws Exception {

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
    @RequestMapping(value = "/getWaybillByWaybillId",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getWaybillByWaybillId(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int waybill_id = Integer.parseInt(jsonObject.getString("waybill_id").trim());

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
    @RequestMapping(value = "/updateCheckTime",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String updateCheckTime(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int id = Integer.parseInt(jsonObject.getString("id").trim());
        int checkTime = Integer.parseInt(jsonObject.getString("checkTime").trim());

        Map params = new HashMap();
        params.put("id", id);
        params.put("checkTime", checkTime);

        clientMapper.updateCheckTime(params);

        ReturnResult returnResult = new ReturnResult(0);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 扫描
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkWaybill",produces = "application/json;charset=utf-8")
    public synchronized String checkWaybill(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int waybill_id = Integer.parseInt(jsonObject.getString("waybill_id").trim());
        int random = Integer.parseInt(jsonObject.getString("random").trim());
        int check_id = Integer.parseInt(jsonObject.getString("client_id").trim());

        Waybill waybill = waybillMapper.getWaybillByWaybill_id(waybill_id);

        ReturnResult returnResult = new ReturnResult();

        if (waybill.getCheck_condition() == 0) {
            if (waybill.getCondition() == 5) {
                if (waybill.getRandom() == random) {
                    Map params = new HashMap();
                    params.put("waybill_id", waybill_id);
                    params.put("check_condition", 1);
                    params.put("check_id", check_id);

                    clientMapper.updateCheckCondition(params);
                    /**
                     * 扫描成功
                     */
                    returnResult.setCode(0);
                    returnResult.setDisplay(0);
                    returnResult.setMessage("");
                    returnResult.setData("");
                } else {
                    /**
                     * 验证码不正确
                     */
                    returnResult.setCode(1);
                    returnResult.setDisplay(0);
                    returnResult.setMessage("");
                    returnResult.setData("");
                }
            } else {
                /**
                 * 此订单还未完成
                 */
                returnResult.setCode(2);
                returnResult.setDisplay(0);
                returnResult.setMessage("");
                returnResult.setData("");
            }
        } else {
            /**
             * 此订单已经扫描过
             */
            returnResult.setCode(3);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData("");
        }

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 根据client_id查询已扫描的waybill
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getScannedWaybillsByClientId",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getScannedWaybillsByClientId(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int client_id = Integer.parseInt(jsonObject.getString("client_id").trim());

        List<Waybill> waybills = clientMapper.getScannedWaybillsByClientId(client_id);

        ReturnResult returnResult = new ReturnResult(0, 0, "", waybills);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 提交已扫描的订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkWaybill2", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String checkWaybill2(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        String waybill_ids = jsonObject.getString("waybill_ids").trim();
        int client_id = Integer.parseInt(jsonObject.getString("client_id").trim());
        double money = Double.parseDouble(jsonObject.getString("money").trim());

        String[] waybill_ids2 = waybill_ids.split(",");
        for (int i=0;i<waybill_ids2.length;i++) {
            clientMapper.updateCheckCondition2(Integer.parseInt(waybill_ids2[i]));
        }

        Map params = new HashMap();
        params.put("submit_time", time);
        params.put("waybill_ids", waybill_ids);
        params.put("client_id", client_id);
        params.put("money", money);

        clientMapper.addCheckSubmit(params);

        ReturnResult returnResult = new ReturnResult(0);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 获取帐户余额
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClientBalance", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getClientBalance(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int id = Integer.parseInt(jsonObject.getString("client_id").trim());
        double balance = clientMapper.getClientBalance(id);

        ReturnResult returnResult = new ReturnResult(0, 0, "", balance);

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 提现
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/drawMoney", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String drawMoney(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        double money = Double.parseDouble(jsonObject.getString("money").trim());
        int client_id = Integer.parseInt(jsonObject.getString("client_id").trim());
        String bank = jsonObject.getString("bank").trim();
        String bankcard = jsonObject.getString("bankcard").trim();
        String realname = jsonObject.getString("realname").trim();
        String draw_password = jsonObject.getString("draw_password").trim();

        Client client = clientMapper.getClientById(client_id);

        ReturnResult returnResult = new ReturnResult();
        if (client.getBalance() >= money) {
            if (client.getDraw_password().equals(draw_password)) {
                Map params = new HashMap();
                params.put("client_id", client_id);
                params.put("bank", bank);
                params.put("bankcard", bankcard);
                params.put("realname", realname);
                params.put("draw_time", time);

                drawMoneyMapper.addDrawMoney(params);

                Map params2 = new HashMap();
                params2.put("money", -money);
                params2.put("id", client_id);
                clientMapper.updateBalance(params2);
                returnResult.setCode(0);
                returnResult.setDisplay(0);
                returnResult.setMessage("");
                returnResult.setData("");
            } else {
                returnResult.setCode(1);
                returnResult.setDisplay(0);
                returnResult.setMessage("");
                returnResult.setData("");
            }
        } else {
            returnResult.setCode(2);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData("");
        }

        return Utils.returnEncrypt(returnResult);
    }

    /**
     *  修改提现密码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateDrawPassword", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String updateDrawPassword(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int id = Integer.parseInt(jsonObject.getString("client_id"));
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");

        Client client = clientMapper.getClientById(id);
        ReturnResult returnResult = new ReturnResult();

        if (client.getDraw_password().equals(oldPassword)) {
            Map params = new HashMap();
            params.put("id", id);
            params.put("draw_password", newPassword);
            clientMapper.updateDrawPassword(params);
            returnResult.setCode(0);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData("");
        } else {
            returnResult.setCode(1);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData("");
        }

        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 设置提现密码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setDrawPassword", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String setDrawPassword(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);

        int id = Integer.parseInt(jsonObject.getString("client_id"));
        String draw_password = jsonObject.getString("draw_password");

        Map params = new HashMap();
        params.put("id", id);
        params.put("draw_password", draw_password);
        clientMapper.updateDrawPassword(params);

        ReturnResult returnResult = new ReturnResult(0);
        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 获取最新公告
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getNewNotice", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getNewNotice() throws Exception {

        Notice notice = noticeMapper.getNewNotice();
        ReturnResult returnResult = new ReturnResult(0, 0, "", notice);
        return Utils.returnEncrypt(returnResult);
    }
}
