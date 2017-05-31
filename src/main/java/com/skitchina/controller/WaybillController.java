package com.skitchina.controller;

import com.skitchina.mapper.ContactMapper;
import com.skitchina.mapper.SubmitMapper;
import com.skitchina.mapper.WaybillMapper;
import com.skitchina.model.Contact;
import com.skitchina.model.ReturnResult;
import com.skitchina.model.Submit;
import com.skitchina.model.Waybill;
import com.skitchina.utils.ReturnResultUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * Created by hu meng on 2017/4/18.
 */
@Controller
@RequestMapping(value = "/waybill")
public class WaybillController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    @Qualifier("waybillMapperImpl")
    private WaybillMapper waybillMapper;

    @Autowired
    @Qualifier("contactMapperImpl")
    private ContactMapper contactMapper;

    @Autowired
    @Qualifier("submitMapperImpl")
    private SubmitMapper submitMapper;

    /**
     * 下单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addWaybill", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String addWaybill(HttpServletRequest request) throws InterruptedException {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String origin = request.getParameter("origin").trim();
        String destination = request.getParameter("destination").trim();
        String consignor_tel = request.getParameter("consignor_tel").trim();
        String consignee_tel = request.getParameter("consignee_tel").trim();
        String consignor_company = request.getParameter("consignor_company").trim();
        String consignee_company = request.getParameter("consignee_company").trim();
        String consignor_address = request.getParameter("consignor_address").trim();
        String consignee_address = request.getParameter("consignee_address").trim();
        double price = Double.parseDouble(request.getParameter("price"));
        double freight = Double.parseDouble(request.getParameter("freight"));
        int payway = Integer.parseInt(request.getParameter("payway"));
        int number = Integer.parseInt(request.getParameter("number"));
        String remark = request.getParameter("remark").trim();

        //先找出这个用户开的最后一单的信息是否和此单一样，如果一样，不开单，防止订单重复
        Waybill waybill0 = waybillMapper.getLastWaybillByUserId(user_id);

        ReturnResult returnResult = new ReturnResult();
        if (waybill0 != null) {
            if (!waybill0.getOrigin().equals(origin) || !waybill0.getDestination().equals(destination) || !waybill0.getConsignor_tel().equals(consignor_tel) ||
                    !waybill0.getConsignee_tel().equals(consignee_tel) || !waybill0.getConsignor_company().equals(consignor_company) ||
                    !waybill0.getConsignee_company().equals(consignee_company) || !waybill0.getConsignor_address().equals(consignor_address) ||
                    !waybill0.getConsignee_address().equals(consignee_address) || waybill0.getPrice() != price || waybill0.getFreight() != freight ||
                    waybill0.getPayway() != payway || waybill0.getNumber() != number) {

                //获取当前时间
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(new Date());

                //保存发货人和收货人信息
                Contact contact1 = contactMapper.getContactByCellphone(consignor_tel);
                Map params1 = new HashMap();
                params1.put("user_id", user_id);
                params1.put("cellphone", consignor_tel);
                params1.put("company", consignor_company);
                params1.put("address", consignor_address);
                if (contact1 != null) {
                    contactMapper.deleteContact(consignor_tel);
                    contactMapper.addContact(params1);
                } else {
                    contactMapper.addContact(params1);
                }

                Contact contact2 = contactMapper.getContactByCellphone(consignee_tel);
                Map params2 = new HashMap();
                params2.put("user_id", user_id);
                params2.put("cellphone", consignee_tel);
                params2.put("company", consignee_company);
                params2.put("address", consignee_address);
                if (contact2 != null) {
                    contactMapper.deleteContact(consignee_tel);
                    contactMapper.addContact(params2);
                } else {
                    contactMapper.addContact(params2);
                }

                //获取最大的waybill_id，并且+1为此运单的单号
                Waybill waybill = waybillMapper.getMaxIdWaybill();
                int waybill_id = waybill.getWaybill_id() + 1;

                //condition为0
                int condition = 0;

                //如果付款方式为现付，则双方的mark改为1
                int consignor_mark = 0;
                int consignee_mark = 0;
                if (payway == 1) {
                    consignee_mark = 1;
                    consignor_mark = 1;
                }

                Map params = new HashMap();

                params.put("user_id", user_id);
                params.put("waybill_id", waybill_id);
                params.put("origin", origin);
                params.put("destination", destination);
                params.put("consignor_tel", consignor_tel);
                params.put("consignee_tel", consignee_tel);
                params.put("consignor_company", consignor_company);
                params.put("consignee_company", consignee_company);
                params.put("consignor_address", consignor_address);
                params.put("consignee_address", consignee_address);
                params.put("price", price);
                params.put("freight", freight);
                params.put("payway", payway);
                params.put("number", number);
                params.put("condition", condition);
                params.put("consignor_mark", consignor_mark);
                params.put("consignee_mark", consignee_mark);
                params.put("remark", remark);
                params.put("time", time);

                waybillMapper.addWaybill(params);
                Waybill waybill1 = waybillMapper.getWaybillByWaybillId(waybill_id);

                /**
                 * 如果是现付单子，自动收运费
                 */
//                if (payway == 1) {
//                    Map params3 = new HashMap();
//                    params3.put("user3_id", user_id);
//                    params3.put("id", waybill1.getId());
//
//                    waybillMapper.updateUser3Id(params3);
//                    waybillMapper.updateConsignorMark(waybill1.getId());
//
//                    Map params4 = new HashMap();
//                    params4.put("user3_time", time);
//                    params4.put("id", waybill1.getId());
//                }
//
//                Waybill waybill2 = waybillMapper.getWaybillByWaybill_id(waybill_id);


                returnResult.setCode(0);
                returnResult.setDisplay(0);
                returnResult.setMessage("");
                returnResult.setData(waybill1);
            } else {
                returnResult.setCode(0);
                returnResult.setDisplay(0);
                returnResult.setMessage("");
                returnResult.setData(waybill0);
            }
        } else {
            //获取当前时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(new Date());

            //保存发货人和收货人信息
            Contact contact1 = contactMapper.getContactByCellphone(consignor_tel);
            Map params1 = new HashMap();
            params1.put("user_id", user_id);
            params1.put("cellphone", consignor_tel);
            params1.put("company", consignor_company);
            params1.put("address", consignor_address);
            if (contact1!=null) {
                contactMapper.deleteContact(consignor_tel);
                contactMapper.addContact(params1);
            }else {
                contactMapper.addContact(params1);
            }

            Contact contact2 = contactMapper.getContactByCellphone(consignee_tel);
            Map params2 = new HashMap();
            params2.put("user_id", user_id);
            params2.put("cellphone", consignee_tel);
            params2.put("company", consignee_company);
            params2.put("address", consignee_address);
            if (contact2 != null) {
                contactMapper.deleteContact(consignee_tel);
                contactMapper.addContact(params2);
            } else {
                contactMapper.addContact(params2);
            }

            //获取最大的waybill_id，并且+1为此运单的单号
            Waybill waybill = waybillMapper.getMaxIdWaybill();
            int waybill_id = waybill.getWaybill_id() + 1;

            //condition为0
            int condition = 0;

            //如果付款方式为现付，则双方的mark改为1
            int consignor_mark = 0;
            int consignee_mark = 0;
            if (payway == 1) {
                consignee_mark = 1;
                consignor_mark = 1;
            }

            Map params = new HashMap();

            params.put("user_id", user_id);
            params.put("waybill_id", waybill_id);
            params.put("origin", origin);
            params.put("destination", destination);
            params.put("consignor_tel", consignor_tel);
            params.put("consignee_tel", consignee_tel);
            params.put("consignor_company", consignor_company);
            params.put("consignee_company", consignee_company);
            params.put("consignor_address", consignor_address);
            params.put("consignee_address", consignee_address);
            params.put("price", price);
            params.put("freight", freight);
            params.put("payway", payway);
            params.put("number", number);
            params.put("condition", condition);
            params.put("consignor_mark", consignor_mark);
            params.put("consignee_mark", consignee_mark);
            params.put("remark", remark);
            params.put("time", time);

            waybillMapper.addWaybill(params);
            Waybill waybill1 = waybillMapper.getWaybillByWaybillId(waybill_id);

            /**
             * 如果是现付单子，自动收运费
             */
//            if (payway == 1) {
//                Map params3 = new HashMap();
//                params3.put("user3_id", user_id);
//                params3.put("id", waybill1.getId());
//
//                waybillMapper.updateUser3Id(params3);
//                waybillMapper.updateConsignorMark(waybill1.getId());
//
//                Map params4 = new HashMap();
//                params4.put("user3_time", time);
//                params4.put("id", waybill1.getId());
//            }
//
//            Waybill waybill2 = waybillMapper.getWaybillByWaybill_id(waybill_id);


            returnResult.setCode(0);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData(waybill1);
        }

        return ReturnResultUtil.ReturnResultToJSON(returnResult);

}
    /**
     * 根据origin分页查询运单，需要定义m
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaybillsByOrigin", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getWaybillsByOrigin(HttpServletRequest request) {
        String origin = request.getParameter("origin");
        int condition = Integer.parseInt(request.getParameter("condition"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));

        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("origin", origin);
        params.put("condition", condition);
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = waybillMapper.getWaybillsByOrigin(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 根据destination分页查询运单，需要定义m
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaybillsByDestination", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getWaybillsByPages(HttpServletRequest request) {
        String destination = request.getParameter("destination");
        int condition = Integer.parseInt(request.getParameter("condition"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));

        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("destination", destination);
        params.put("condition", condition);
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = waybillMapper.getWaybillsByDestination(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 根据user_id查询订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaybillsByUserId", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getWaybillsByUserId(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));

        int m = (pages - 1) * rows;

        Map params = new HashMap();

        params.put("user_id", user_id);
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = waybillMapper.getWaybillsByUserId(params);

        ReturnResult returnResult = new ReturnResult();

        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 修改订单状态
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCondition", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String updateCondition(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int id = Integer.parseInt(request.getParameter("id"));
        int condition = Integer.parseInt(request.getParameter("condition"));

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("id", id);

        if (condition == 1) {
            params.put("time1", time);
            waybillMapper.updateCondition1(params);
            Map params1 = new HashMap();
            params1.put("id", id);
            params1.put("user1_id", user_id);
            waybillMapper.updateUser1Id(params1);
        } else if (condition == 2) {
            params.put("time2", time);
            waybillMapper.updateCondition2(params);
        } else if (condition == 4) {
            params.put("time4", time);
            waybillMapper.updateCondition4(params);
        } else if (condition == 5) {
            params.put("time5", time);
            waybillMapper.updateCondition5(params);
        }

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("1");


        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 根据waybill_id查询运单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaybillByWaybillId", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getWaybillByWaybillId(HttpServletRequest request) {

        int waybill_id = Integer.parseInt(request.getParameter("waybill_id"));

        Waybill waybill = waybillMapper.getWaybillByWaybillId(waybill_id);

        ReturnResult returnResult = new ReturnResult();
        if (waybill != null) {
            returnResult.setCode(0);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
            returnResult.setData(waybill);
        } else if (waybill == null) {
            returnResult.setCode(1);
            returnResult.setDisplay(0);
            returnResult.setMessage("");
        }

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 收账
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/receive", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String receive(HttpServletRequest request) {
        //寄付：0为收运费，1为货款
        // 到付：2为运费+货款
        int type = Integer.parseInt(request.getParameter("type"));
        int id = Integer.parseInt(request.getParameter("id"));
        int user2_id = Integer.parseInt(request.getParameter("user2_id"));

        Waybill waybill = waybillMapper.getWaybillById(id);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setDisplay(0);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time3 = simpleDateFormat.format(new Date());

            if (type == 0) {
                /**
                 * 0为只收运费，修改user3_id为此用户id，修改consignor_mark为0
                 * 判断consignee_mark是否为0，如果为0，修改condition为3
                 */
                Map params = new HashMap();
                params.put("user3_id", user2_id);
                params.put("id", id);
                waybillMapper.updateUser3Id(params);
                waybillMapper.updateConsignorMark(id);
                Map params2 = new HashMap();
                params2.put("id", id);
                params2.put("user3_time", time3);
                waybillMapper.updateUser3Time(params2);
                if (waybill.getConsignee_mark() == 0 || waybill.getConsignee_mark() == 2) {
                    Map params1 = new HashMap();
                    params1.put("id", id);
                    params1.put("time3", time3);
                    waybillMapper.updateCondition3(params1);
                }
                returnResult.setCode(0);
                returnResult.setMessage("运费已收");
            } else if (type == 1) {
                /**
                 * 1为只收货款，修改consignee_mark为0，修改user2_id为此用户id
                 * 判断consignor_mark是否为0，如果试0，修改condition为3
                 */
                Map params = new HashMap();
                params.put("user2_id", user2_id);
                params.put("id", id);
                waybillMapper.updateWaybillUser2Id(params);
                waybillMapper.updateConsigneeMark(id);
                Map params2 = new HashMap();
                params2.put("id", id);
                params2.put("user2_time", time3);
                waybillMapper.updateUser2Time(params2);
                if (waybill.getConsignor_mark() == 0 || waybill.getConsignor_mark() == 2) {
                    Map params1 = new HashMap();
                    params1.put("id", id);
                    params1.put("time3", time3);
                    waybillMapper.updateCondition3(params1);
                }
                returnResult.setCode(0);
                returnResult.setMessage("代收款已收");
            } else if (type == 2) {
                /**
                 * 2为到付运单，不用修改consignor_mark和consignee_mark
                 * 将user2_id修改为此用户id，修改condition为3
                 */
                Map params = new HashMap();
                params.put("user2_id", user2_id);
                params.put("id", id);
                params.put("time3", time3);
                waybillMapper.receive(params);
                Map params2 = new HashMap();
                params2.put("id", id);
                params2.put("user2_time", time3);
                waybillMapper.updateUser2Time(params2);
                returnResult.setCode(0);
                returnResult.setMessage("收账成功");
            } else {
                returnResult.setCode(1);
                returnResult.setMessage("收账失败，请稍后再试");
            }

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 根据user2_id查询已收款但是没有交账的运单
     * @param request
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/getReceivableWaybillsAndNotSubmit", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
//    public String getReceivableWaybillsAndNotSubmit(HttpServletRequest request) {
//        int user2_id = Integer.parseInt(request.getParameter("user2_id"));
//        int pages = Integer.parseInt(request.getParameter("pages"));
//        int rows = Integer.parseInt(request.getParameter("rows"));
//        int m = (pages - 1) * rows;
////        //到付单子
////        List<Waybill> waybills1;
////        //寄付单子，发货方，收的运费
////        List<Waybill> waybills2;
////        //寄付单子，收货方，收的货款
////        List<Waybill> waybills3;
////
////        waybills1 = waybillMapper.getReceivableWaybillsAndNotSubmit(user2_id);
////        waybills2 = waybillMapper.getFreightReceivableWaybills(user2_id);
////        waybills3 = waybillMapper.getPriceReceivableWaybills(user2_id);
////
////        Map waybills = new HashMap();
////        waybills.put("waybills1", waybills1);
////        waybills.put("waybills2", waybills2);
////        waybills.put("waybills3", waybills3);
//
//        Map params = new HashMap();
//        params.put("user2_id", user2_id);
//        params.put("m", m);
//        params.put("rows", rows);
//
//        List<Waybill> waybills = waybillMapper.getReceivableWaybillsAndNotSubmit(params);
//
//        ReturnResult returnResult = new ReturnResult();
//        returnResult.setCode(0);
//        returnResult.setDisplay(0);
//        returnResult.setMessage("");
//        returnResult.setData(waybills);
//
//        return ReturnResultUtil.ReturnResultToJSON(returnResult);
//    }

    /**
     * 根据user2_id查询已交账但未完成的运单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getSubmitWaybillsAndNotComplete", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getSubmitWaybillsAndNotComplete(HttpServletRequest request) {

        int user2_id = Integer.parseInt(request.getParameter("user2_id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;
//        //到付单子
//        List<Waybill> waybills1;
//        //寄付，运费已交账
//        List<Waybill> waybills2;
//        //寄付，货款已交账
//        List<Waybill> waybills3;
//
//        waybills1 = waybillMapper.getSubmitWaybillsAndNotComplete(user2_id);
//        waybills2 = waybillMapper.getFreightSubmitWaybills(user2_id);
//        waybills3 = waybillMapper.getPriceSubmitWaybills(user2_id);
//
//        Map waybills = new HashMap();
//        waybills.put("waybills1", waybills1);
//        waybills.put("waybills2", waybills2);
//        waybills.put("waybills3", waybills3);
        Map params = new HashMap();
        params.put("user2_id", user2_id);
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = waybillMapper.getSubmitWaybillsAndNotComplete(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 一键完成所有运单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/completeWaybills", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String completeWaybills(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time5 = simpleDateFormat.format(new Date());

        Submit submit = submitMapper.getSubmitById(id);
        String ids = submit.getFreight();
        String ids2 = submit.getPrice();
        String ids3 = submit.getFreightandprice();

        if (!ids.equals("no")) {
            String[] ids_id = ids.split(",");
            for (int i = 0; i < ids_id.length; i++) {
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids_id[i]));
                waybillMapper.updateConsignorMark3(Integer.parseInt(ids_id[i]));
                if (waybill.getConsignee_mark() == 3) {
                    Map params = new HashMap();
                    params.put("id", ids_id[i]);
                    params.put("time5", time5);
                    waybillMapper.updateCondition5(params);
                }
            }
        }

        if (!ids2.equals("no")) {
            String[] ids2_id = ids2.split(",");
            for (int i=0;i<ids2_id.length;i++) {
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids2_id[i]));
                waybillMapper.updateConsigneeMark3(Integer.parseInt(ids2_id[i]));
                if (waybill.getConsignor_mark() == 3) {
                    Map params = new HashMap();
                    params.put("id", ids2_id[i]);
                    params.put("time5", time5);
                    waybillMapper.updateCondition5(params);
                }
            }
        }

        if (!ids3.equals("no")) {
            String[] ids3_id = ids3.split(",");
            for (int i=0;i<ids3_id.length;i++) {
                Map params = new HashMap();
                params.put("id", ids3_id[i]);
                params.put("time5", time5);
                waybillMapper.updateCondition5(params);
            }
        }

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(1);
        returnResult.setMessage("成功完成所有订单");

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 完成单个运单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/completeWaybill", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String completeWaybill(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Waybill waybill = waybillMapper.getWaybillById(id);
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(1);
        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time5 = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("id", id);
        params.put("time5", time5);
        if (waybill.getConsignor_mark() == 0) {
            waybillMapper.updateCondition5(params);
            returnResult.setMessage("完成运单");
        } else {
            returnResult.setMessage("运费还没有提交");
        }
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 作废运单
     * 返回code 为0 成功
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/invalidWaybill", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String invalidWaybill(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        waybillMapper.invalidWaybill(id);
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 根据user_id和time4查询运单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getWaybillsByUserIdAndTime4", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getWaybillsByUserIdAndTime4(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int id = Integer.parseInt(request.getParameter("id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        List<Waybill> waybills1 = new ArrayList<Waybill>();
        List<Waybill> waybills2 = new ArrayList<Waybill>();
        List<Waybill> waybills3 = new ArrayList<Waybill>();

        Submit submit = submitMapper.getSubmitById(id);
        //运费已交账
        String ids = submit.getFreight();
        if (!ids.equals("no")) {
            String[] ids_id = ids.split(",");
            for (int i = 0; i < ids_id.length; i++) {
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids_id[i]));
                waybills1.add(waybill);
            }
        }
        //货款已交账
        String ids2 = submit.getPrice();
        if (!ids2.equals("no")) {
            String[] ids2_id = ids2.split(",");
            for (int i=0;i<ids2_id.length;i++) {
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids2_id[i]));
                waybills2.add(waybill);
            }
        }
        //到付已交账
        String ids3 = submit.getFreightandprice();
        if (!ids3.equals("no")) {
            String[] ids3_id = ids3.split(",");
            for (int i=0;i<ids3_id.length;i++) {
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids3_id[i]));
                waybills3.add(waybill);
            }
        }

        Map waybills = new HashMap();
        waybills.put("waybills1", waybills1);
        waybills.put("waybills2", waybills2);
        waybills.put("waybills3", waybills3);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 根据订单号或者发货公司或者收货公司查询订单
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getWaybillsByWaybillIdOrConsignorCompanyOrConsigneeCompany",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getWaybillsByWaybillIdOrConsignorCompannyOrConsigneeCompany(HttpServletRequest request) {
        String str = request.getParameter("str");
        int type = Integer.parseInt(request.getParameter("type"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        List<Waybill> waybills = new ArrayList<Waybill>();

        if (type == 1) {
            //根据waybill_id查询
            Waybill waybill = waybillMapper.getWaybillByWaybill_id(Integer.parseInt(str));
            waybills.add(waybill);
        } else if (type == 2) {
            //根据ConsignorCompanny查询
            Map params = new HashMap();
            params.put("consignor_company", str);
            params.put("m", m);
            params.put("rows", rows);
            waybills = waybillMapper.getWaybillsByConsignorCompany(params);
        } else if (type == 3) {
            //根据ConsigneeCompany查询
            Map params = new HashMap();
            params.put("consignee_company", str);
            params.put("m", m);
            params.put("rows", rows);
            waybills = waybillMapper.getWaybillsByConsigneeCompany(params);
        }

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 网点做账 按照时间排序
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getWaybillsNotSubmitOrderByTime", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getWaybillsNotSubmitOrderByTime(HttpServletRequest request) {
        int user2_id = Integer.parseInt(request.getParameter("user2_id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("user2_id", user2_id);
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = waybillMapper.getWaybillsNotSubmit(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 我的现付
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFreightReceivableWaybills",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getFreightReceivableWaybills(HttpServletRequest request) {
        int user3_id = Integer.parseInt(request.getParameter("user_id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("user3_id", user3_id);
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = waybillMapper.getFreightReceivableWaybills(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 测试
     * @param
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/test", produces = "application/json;charset=utf-8", method = RequestMethod.GET)
//    public String getLastWaybillByUserId() {
//        int user_id = 3;
//        Waybill waybill = waybillMapper.getLastWaybillByUserId(user_id);
//        System.out.println(waybill==null);
//        ReturnResult returnResult = new ReturnResult();
//        returnResult.setData(waybill);
//        returnResult.setCode(0);
//        returnResult.setMessage("");
//        returnResult.setDisplay(0);
//
//        return ReturnResultUtil.ReturnResultToJSON(returnResult);
//    }
}
