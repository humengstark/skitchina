package com.skitchina.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.skitchina.mapper.*;
import com.skitchina.model.*;
import com.skitchina.utils.ReturnResultUtil;
import com.skitchina.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hu meng on 2017/4/15.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    @Qualifier("userMapperImpl")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("submitMapperImpl")
    private SubmitMapper submitMapper;

    @Autowired
    @Qualifier("waybillMapperImpl")
    private WaybillMapper waybillMapper;

    @Autowired
    @Qualifier("clientWaybillMapperImpl")
    private ClientWaybillMapper clientWaybillMapper;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addUser", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String addUser(HttpServletRequest request) {
        String cellphone = request.getParameter("cellphone");
        String password = request.getParameter("password");
        String company_name = request.getParameter("company_name");
        String company_address = request.getParameter("company_address");
        String company_tel = request.getParameter("company_tel");
        String name = request.getParameter("name");

        logger.info("cellphone=" + cellphone);
        logger.info("password=" + password);
        logger.info("company_name=" + company_name);
        logger.info("company_address=" + company_address);
        logger.info("company_tel=" + company_tel);
        logger.info("name=" + name);

        Map params = new HashMap();
        params.put("cellphone", cellphone);
        params.put("password", password);
        params.put("company_name", company_name);
        params.put("company_address", company_address);
        params.put("company_tel", company_tel);
        params.put("name", name);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(1);
        User user = userMapper.getUserByCellphone(cellphone);
        if (user == null) {
            userMapper.addUser(params);
            returnResult.setMessage("注册成功");
        } else {
            returnResult.setMessage("此用户已存在");
        }

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 用户登陆
     * message 0为登陆成功，1帐户不存在，2密码错误
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/userLogin", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String userLogin(HttpServletRequest request) {
        String cellphone1 = request.getParameter("cellphone");
        String password1 = request.getParameter("password");
//        String registration_id = request.getParameter("registration_id");
        logger.info("参数为：cellphone:"+cellphone1+"，password:"+password1);
        User user = userMapper.getUserByCellphone(cellphone1);
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(1);
        if (user != null) {
            if (user.getPassword().equals(password1)) {
                returnResult.setCode(0);
                returnResult.setData(user);
//                Map params = new HashMap();
//                params.put("id", user.getId());
//                params.put("registration_id", registration_id);
//                userMapper.updateRegistrationId(params);
            } else {
                returnResult.setCode(1);
                returnResult.setMessage("密码错误");
            }
        } else {
            returnResult.setCode(1);
            returnResult.setMessage("帐号不存在");
        }
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 交账
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/submit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String submit(HttpServletRequest request) {
//        int waybill_num = Integer.parseInt(request.getParameter("waybill_num"));
        double money = Double.parseDouble(request.getParameter("money"));

        //货款的id号
        String ids = request.getParameter("ids");
        //运费的id号
        String ids3 = request.getParameter("ids3");
        //到付的id号
        String ids2 = request.getParameter("ids2");

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        User user = userMapper.getUserById(user_id);

        int idsLength, idsLength2, idsLength3;

        String[] ids_id = ids.split(",");
        if (!ids.equals("no")) {
            idsLength = ids_id.length;
        } else {
            idsLength = 0;
        }
        String[]  ids3_id= ids3.split(",");
        if (!ids3.equals("no")) {
            idsLength3 = ids3_id.length;
        } else {
            idsLength3 = 0;
        }
        String[] ids2_id = ids2.split(",");
        if (!ids2.equals("no")) {
            idsLength2 = ids2_id.length;
        } else {
            idsLength2 = 0;
        }

        ReturnResult returnResult = new ReturnResult();
//        if (idsLength + idsLength2 + idsLength3 == waybill_num) {
            if (1==1){
            //获取当前时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time4 = simpleDateFormat.format(new Date());

            if (!ids.equals("no")) {
                for (int i = 0; i < ids_id.length; i++) {
                    Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids_id[i]));
                    waybillMapper.updateConsigneeMark2(Integer.parseInt(ids_id[i]));
                    if (waybill.getConsignor_mark() == 2 || waybill.getConsignor_mark() == 3) {
                        Map params1 = new HashMap();
                        params1.put("time4", time4);
                        params1.put("id", ids_id[i]);
                        waybillMapper.updateCondition4(params1);
                    }
                }
            }

            if (!ids3.equals("no")) {
                for (int i = 0; i < ids3_id.length; i++) {
                    Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids3_id[i]));
                    waybillMapper.updateConsignorMark2(Integer.parseInt(ids3_id[i]));
                    if (waybill.getConsignee_mark() == 2 || waybill.getConsignee_mark() == 3) {
                        Map params1 = new HashMap();
                        params1.put("time4", time4);
                        params1.put("id", ids3_id[i]);
                        waybillMapper.updateCondition4(params1);
                    }
                }
            }

            if (!ids2.equals("no")) {
                for (int i = 0; i < ids2_id.length; i++) {
                    Map params1 = new HashMap();
                    params1.put("time4", time4);
                    params1.put("id", ids2_id[i]);
                    waybillMapper.updateCondition4(params1);
                }
            }

            Map params = new HashMap();
            params.put("user2_id", user_id);
            params.put("achievement", money);
            params.put("time4", time4);
            params.put("name", user.getName());
            params.put("freight", ids3);
            params.put("price", ids);
            params.put("freightandprice", ids2);

            submitMapper.addSubmit(params);

            returnResult.setCode(0);
            returnResult.setDisplay(1);
            returnResult.setMessage("交账成功");

        } else {

            returnResult.setCode(1);
            returnResult.setDisplay(1);
            returnResult.setMessage("交账失败，请重试");
        }

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 查询所有人的总账
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllUsersAchievement",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getAllUsersAchievement(HttpServletRequest request) {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);

        List<Submit> submits = submitMapper.getAllSubmits(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(submits);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 获取可以接的单子
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getClientWaybills", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getClientWaybills(HttpServletRequest request) {
        String station = request.getParameter("station");
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;
        System.out.println("station="+station+"，pages="+pages+"，m="+m+"，rows="+rows);
        Map params = new HashMap();
        params.put("station", station);
        params.put("m", m);
        params.put("rows", rows);

        List<ClientWaybill> clientWaybills = clientWaybillMapper.getClientWaybills(params);

        ReturnResult returnResult = new ReturnResult(0, 0, "", clientWaybills);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 查询从用户那边接了的单子
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getReceivebleClientWaybills", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getReceivebleClientWaybills(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("user_id", user_id);
        params.put("rows", rows);
        params.put("m", m);
        List<ClientWaybill> clientWaybills = clientWaybillMapper.getReceivebleClientWaybills(params);

        ReturnResult returnResult = new ReturnResult(0, 0, "", clientWaybills);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }


    /**
     * 获取NewWaybillNum
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateNewWaybillNum",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String updateNewWaybillNum(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int new_waybill= userMapper.getNewWaybillNum(user_id);
        ReturnResult returnResult = new ReturnResult(0, 0, "", new_waybill);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 将NewWaybillNum清零
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setNewWaybill0", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String setNewWaybill0(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        userMapper.setNewWaybill0(user_id);
        ReturnResult returnResult = new ReturnResult(0);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

}

