package com.skitchina.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skitchina.mapper.*;
import com.skitchina.model.*;
import com.skitchina.utils.JpushClientUtil;
import com.skitchina.utils.MsgUtil;
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
import java.util.*;

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

    @Autowired
    @Qualifier("stationMapperImpl")
    private StationMapper stationMapper;

    @Autowired
    @Qualifier("userMapperImpl")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("questionMapperImpl")
    private QuestionMapper questionMapper;

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
        String station = jsonObject.getString("station").trim();
        int long_id = clientMapper.getMaxLongId() + 1;
        Map params = new HashMap();

        params.put("cellphone", cellphone);
        params.put("password", password);
        params.put("company_name", company_name);
        params.put("company_address", company_address);
        params.put("company_tel", company_tel);
        params.put("name", name);
        params.put("long_id", long_id);
        params.put("station", station);

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

        ReturnClient client = clientMapper.getClientByCellphone(cellphone);
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
        System.out.println(json);
        JSONObject jsonObject = JSON.parseObject(json);
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("client_id", Integer.parseInt(jsonObject.getString("client_id").trim()));
        params.put("time1", time);
        params.put("origin", jsonObject.getString("origin"));
        params.put("destination", jsonObject.getString("destination"));
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

        int result = JpushClientUtil.sendToTag(jsonObject.getString("origin"));

        if (result == 1) {
            System.out.println("推送成功");
            userMapper.updateNewWaybillNum(jsonObject.getString("origin"));
        } else {
            System.out.println("推送失败");
        }

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
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int week = cal.get(Calendar.DAY_OF_WEEK);

        String waybill_ids = jsonObject.getString("waybill_ids").trim();
        int client_id = Integer.parseInt(jsonObject.getString("client_id").trim());
        double money = Double.parseDouble(jsonObject.getString("money").trim());

        Client client = clientMapper.getClientById(client_id);
        int client_week = client.getCheckTime();

        ReturnResult returnResult = new ReturnResult();

        if (client_week == week - 1 || client_week == week - 4) {
            String[] waybill_ids2 = waybill_ids.split(",");
            for (int i = 0; i < waybill_ids2.length; i++) {
                clientMapper.updateCheckCondition2(Integer.parseInt(waybill_ids2[i]));
            }

            Map params = new HashMap();
            params.put("submit_time", time);
            params.put("waybill_ids", waybill_ids);
            params.put("client_id", client_id);
            params.put("money", money);

            //0为对账成功
            clientMapper.addCheckSubmit(params);
            returnResult.setCode(0);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData("");
        } else {
            //1为对账日期不对
            returnResult.setCode(1);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData("");
        }

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
                params.put("money", money);

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

    /**
     * 获取所有网点
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getAllStations", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getAllStations() throws Exception {
        List<Station> stations = stationMapper.getAllStations();
        ReturnResult returnResult = new ReturnResult(0, 0, "", stations);
        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 获取对账记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getCheckSubmits", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getCheckSubmits(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);
        int client_id = jsonObject.getIntValue("client_id");
        int pages = jsonObject.getIntValue("pages");
        int rows = jsonObject.getIntValue("rows");
        int m = (pages - 1) * rows;
        Map params = new HashMap();
        params.put("client_id", client_id);
        params.put("m", m);
        params.put("rows", rows);

        List<CheckSubmit> checkSubmits = clientMapper.getCheckSubmits(params);

        ReturnResult returnResult = new ReturnResult(0, 0, "", checkSubmits);
        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 获取提现记录
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getDrawMoneys", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getDrawMoneys(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);
        int client_id = jsonObject.getIntValue("client_id");
        int pages = jsonObject.getIntValue("pages");
        int rows = jsonObject.getIntValue("rows");
        int m = (pages - 1) * rows;
        Map params = new HashMap();
        params.put("client_id", client_id);
        params.put("m", m);
        params.put("rows", rows);

        List<DrawMoney> drawMoneys = clientMapper.getDrawMoneys(params);
        ReturnResult returnResult = new ReturnResult(0, 0, "", drawMoneys);
        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 获取对账成功的记录
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCheckSubmitsSuccess", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getCheckSubmitsSuccess(HttpServletRequest request) throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);
        int client_id = jsonObject.getIntValue("client_id");
        int pages = jsonObject.getIntValue("pages");
        int rows = jsonObject.getIntValue("rows");
        int m = (pages - 1) * rows;
        Map params = new HashMap();
        params.put("client_id", client_id);
        params.put("m", m);
        params.put("rows", rows);

        List<CheckSubmit> checkSubmits = clientMapper.getCheckSubmitsSuccess(params);

        ReturnResult returnResult = new ReturnResult(0, 0, "", checkSubmits);
        return Utils.returnEncrypt(returnResult);
    }

    /**
     * 查询个人信息
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getClientById",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getClientById(HttpServletRequest request)throws Exception {
        String json = Utils.getJsonString(request);
        JSONObject jsonObject = JSON.parseObject(json);
        int client_id = jsonObject.getIntValue("client_id");
        Client client = clientMapper.getClientById(client_id);
        return Utils.returnEncrypt(new ReturnResult(0, 0, "", client));
    }

    /**
     * 获取常见问题
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getAllQuestions",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getAllQuestions()throws Exception {
        List<Question> questionList = questionMapper.getAllQuestions();
        return Utils.returnEncrypt(new ReturnResult(0, 0, "", questionList));
    }

    /**
     * 发送验证码
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/getCheckCode", produces = "appliaction/json;charset=utf-8", method = RequestMethod.POST)
    public String getCheckCode(HttpServletRequest request) throws Exception {
        String cellphone = request.getParameter("cellphone");
        int checkCode = MsgUtil.getCheckCode();
        String content = "您正在修改密码，验证码为：[" + checkCode + "]";

        Map params = new HashMap();
        params.put("cellphone", cellphone);
        params.put("checkCode", String.valueOf(checkCode));

        clientMapper.updateCheckCodeByCellphone(params);

        MsgUtil.sendSms(cellphone, content);

        return Utils.returnEncrypt(new ReturnResult(0));
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePassword", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String updatePassword(HttpServletRequest request)throws Exception {
        String cellphone = request.getParameter("cellphone");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");
        Client client = clientMapper.getClientByCellphone2(cellphone);

        if (client.getCheckCode().equals(checkCode)) {
            Map params = new HashMap();
            params.put("cellphone", cellphone);
            params.put("password", password);
            clientMapper.updatePassword(params);
            return Utils.returnEncrypt(new ReturnResult(0));
        }

        return Utils.returnEncrypt(new ReturnResult(1, 1, "验证码错误！", null));
    }
}
