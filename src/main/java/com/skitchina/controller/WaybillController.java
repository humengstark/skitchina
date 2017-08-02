package com.skitchina.controller;

import com.skitchina.mapper.*;
import com.skitchina.model.*;
import com.skitchina.utils.ReturnResultUtil;
import com.skitchina.utils.Utils;
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

    @Autowired
    @Qualifier("userMapperImpl")
    private UserMapper userMapper;

    @Autowired
    @Qualifier("clientWaybillMapperImpl")
    private ClientWaybillMapper clientWaybillMapper;

    /**
     * �µ�
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

        //���ҳ�����û��������һ������Ϣ�Ƿ�ʹ˵�һ�������һ��������������ֹ�����ظ�
        Waybill waybill0 = waybillMapper.getLastWaybillByUserId(user_id);

        //����һ��4λ�����
        int random = Utils.getRandomNum();

        ReturnResult returnResult = new ReturnResult();
        if (1 == 1) {
//            if (!waybill0.getOrigin().equals(origin) || !waybill0.getDestination().equals(destination) || !waybill0.getConsignor_tel().equals(consignor_tel) ||
//                    !waybill0.getConsignee_tel().equals(consignee_tel) || !waybill0.getConsignor_company().equals(consignor_company) ||
//                    !waybill0.getConsignee_company().equals(consignee_company) || !waybill0.getConsignor_address().equals(consignor_address) ||
//                    !waybill0.getConsignee_address().equals(consignee_address) || waybill0.getPrice() != price || waybill0.getFreight() != freight ||
//                    waybill0.getPayway() != payway || waybill0.getNumber() != number) {
            if (1 == 1) {

                //��ȡ��ǰʱ��
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(new Date());

                //���淢���˺��ջ�����Ϣ
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

                //��ȡ����waybill_id������+1Ϊ���˵��ĵ���
                Waybill waybill = waybillMapper.getMaxIdWaybill();
                int waybill_id = waybill.getWaybill_id() + 1;

                //conditionΪ0
                int condition = 0;

                //������ʽΪ�ָ�����˫����mark��Ϊ1
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
                params.put("random", random);

                waybillMapper.addWaybill(params);
                Waybill waybill1 = waybillMapper.getWaybillByWaybillId(waybill_id);

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
            //��ȡ��ǰʱ��
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(new Date());

            //���淢���˺��ջ�����Ϣ
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

            //��ȡ����waybill_id������+1Ϊ���˵��ĵ���
            Waybill waybill = waybillMapper.getMaxIdWaybill();
            int waybill_id = waybill.getWaybill_id() + 1;

            //conditionΪ0
            int condition = 0;

            //������ʽΪ�ָ�����˫����mark��Ϊ1
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
            params.put("random", random);

            waybillMapper.addWaybill(params);
            Waybill waybill1 = waybillMapper.getWaybillByWaybillId(waybill_id);

            /**
             * ������ָ����ӣ��Զ����˷�
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
     * ����origin��ѯ����
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
     * ����destination��ѯ����
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
     * ����user_id��ѯ����
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
     * �޸Ķ���״̬
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateCondition", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String updateCondition(HttpServletRequest request) {
//        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int id = Integer.parseInt(request.getParameter("id"));
        int condition = Integer.parseInt(request.getParameter("condition"));

        //��ȡ��ǰʱ��
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("id", id);

        if (condition == 1) {
            params.put("time1", time);
            waybillMapper.updateCondition1(params);
//            Map params1 = new HashMap();
//            params1.put("id", id);
//            params1.put("user1_id", user_id);
//            waybillMapper.updateUser1Id(params1);
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
     * ����waybill_id��ѯ����
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
     * ����
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/receive", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String receive(HttpServletRequest request) {
        //�ĸ���0Ϊ���˷ѣ�1Ϊ����
        // ������2Ϊ�˷�+����
        int type = Integer.parseInt(request.getParameter("type"));
        int id = Integer.parseInt(request.getParameter("id"));
        int user2_id = Integer.parseInt(request.getParameter("user2_id"));

        Waybill waybill = waybillMapper.getWaybillById(id);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setDisplay(0);

        //��ȡ��ǰʱ��
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time3 = simpleDateFormat.format(new Date());

            if (type == 0) {
                /**
                 * 0Ϊֻ���˷ѣ��޸�user3_idΪ���û�id���޸�consignor_markΪ0
                 * �ж�consignee_mark�Ƿ�Ϊ0�����Ϊ0���޸�conditionΪ3
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
                returnResult.setMessage("�˷�����");
            } else if (type == 1) {
                /**
                 * 1Ϊֻ�ջ���޸�consignee_markΪ0���޸�user2_idΪ���û�id
                 * �ж�consignor_mark�Ƿ�Ϊ0���Թ���0���޸�conditionΪ3
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
                returnResult.setMessage("���տ�����");
            } else if (type == 2) {
                /**
                 * 2Ϊ�����˵�
                 * ��user2_id�޸�Ϊ���û�id���޸�conditionΪ3
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
                returnResult.setMessage("���˳ɹ�");
            } else {
                returnResult.setCode(1);
                returnResult.setMessage("����ʧ�ܣ����Ժ�����");
            }

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * ����user2_id��ѯ���տ��û�н��˵��˵�
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
////        //��������
////        List<Waybill> waybills1;
////        //�ĸ����ӣ����������յ��˷�
////        List<Waybill> waybills2;
////        //�ĸ����ӣ��ջ������յĻ���
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
     * ����user2_id��ѯ�ѽ��˵���δ��ɵĶ���
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
//        //��������
//        List<Waybill> waybills1;
//        //�ĸ����˷��ѽ���
//        List<Waybill> waybills2;
//        //�ĸ��������ѽ���
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
     * һ��������ж���
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/completeWaybills", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String completeWaybills(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));

        //��ȡ��ǰʱ��
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
                    clientWaybillMapper.updateClientWaybillCondition2(waybill.getWaybill_id());
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
                    clientWaybillMapper.updateClientWaybillCondition2(waybill.getWaybill_id());
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
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids3_id[i]));
                clientWaybillMapper.updateClientWaybillCondition2(waybill.getWaybill_id());
            }
        }

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(1);
        returnResult.setMessage("�ɹ�������ж���");

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * ��ɵ�������
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
        //��ȡ��ǰʱ��
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time5 = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("id", id);
        params.put("time5", time5);
        if (waybill.getConsignor_mark() == 0) {
            waybillMapper.updateCondition5(params);
            returnResult.setMessage("��ɶ���");
        } else {
            returnResult.setMessage("�˷ѻ�û���ύ");
        }
        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * ���϶���
     * ����code Ϊ0�ɹ�
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
     * ����user_id��time4��ѯ����
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
        //�˷ѵĶ���
        String ids = submit.getFreight();
        if (!ids.equals("no")) {
            String[] ids_id = ids.split(",");
            for (int i = 0; i < ids_id.length; i++) {
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids_id[i]));
                waybills1.add(waybill);
            }
        }
        //����
        String ids2 = submit.getPrice();
        if (!ids2.equals("no")) {
            String[] ids2_id = ids2.split(",");
            for (int i=0;i<ids2_id.length;i++) {
                Waybill waybill = waybillMapper.getWaybillById(Integer.parseInt(ids2_id[i]));
                waybills2.add(waybill);
            }
        }
        //����
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
     * ��������������
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
            //����waybill_id��ѯ
            Waybill waybill = waybillMapper.getWaybillByWaybill_id(Integer.parseInt(str));
            waybills.add(waybill);
        } else if (type == 2) {
            //����ConsignorCompanny��ѯ
            Map params = new HashMap();
            params.put("consignor_company", str);
            params.put("m", m);
            params.put("rows", rows);
            waybills = waybillMapper.getWaybillsByConsignorCompany(params);
        } else if (type == 3) {
            //����ConsigneeCompany��ѯ
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
     * ��������
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
     * �ҵ��ָ�
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFreightReceivableWaybills",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getFreightReceivableWaybills(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        User user = userMapper.getUserById(user_id);
        Map params = new HashMap();
        String origin = user.getStation().trim();
        params.put("origin", origin);
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = waybillMapper.getFreightReceivableWaybillsByStation(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(waybills);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * ��client����ӵ������µ��µ��ӿڣ�
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/receiveClientWaybill", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public synchronized String receiveClientWaybill(HttpServletRequest request) throws InterruptedException {
        int client_id = Integer.parseInt(request.getParameter("client_id"));
        int clientWaybill_id = Integer.parseInt(request.getParameter("clientWaybill_id"));
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

        ClientWaybill clientWaybill = clientWaybillMapper.getClientWaybillById(clientWaybill_id);

        //���ҳ�����û��������һ������Ϣ�Ƿ�ʹ˵�һ�������һ��������������ֹ�����ظ�
        Waybill waybill0 = waybillMapper.getLastWaybillByUserId(user_id);

        //����һ��4λ�����
        int random = Utils.getRandomNum();

        ReturnResult returnResult = new ReturnResult();
        if (1==1) {
//            if (!waybill0.getOrigin().equals(origin) || !waybill0.getDestination().equals(destination) || !waybill0.getConsignor_tel().equals(consignor_tel) ||
//                    !waybill0.getConsignee_tel().equals(consignee_tel) || !waybill0.getConsignor_company().equals(consignor_company) ||
//                    !waybill0.getConsignee_company().equals(consignee_company) || !waybill0.getConsignor_address().equals(consignor_address) ||
//                    !waybill0.getConsignee_address().equals(consignee_address) || waybill0.getPrice() != price || waybill0.getFreight() != freight ||
//                    waybill0.getPayway() != payway || waybill0.getNumber() != number) {
            if (1==1) {

                //��ȡ��ǰʱ��
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(new Date());

                //���淢���˺��ջ�����Ϣ
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

                //��ȡ����waybill_id������+1Ϊ���˵��ĵ���
                Waybill waybill = waybillMapper.getMaxIdWaybill();
                int waybill_id = waybill.getWaybill_id() + 1;

                //conditionΪ0
                int condition = 0;

                //������ʽΪ�ָ�����˫����mark��Ϊ1
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
                params.put("random", random);

                waybillMapper.addWaybill(params);
                Waybill waybill1 = waybillMapper.getWaybillByWaybillId(waybill_id);

                Map params3 = new HashMap();
                params3.put("clientWaybill_id", clientWaybill_id);
                params3.put("user_id", user_id);
                params3.put("client_id", client_id);
                params3.put("waybill_id", waybill_id);
                clientWaybillMapper.updateClientWaybillCondition(params3);

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
            //��ȡ��ǰʱ��
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = simpleDateFormat.format(new Date());

            //���淢���˺��ջ�����Ϣ
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

            //��ȡ����waybill_id������+1Ϊ���˵��ĵ���
            Waybill waybill = waybillMapper.getMaxIdWaybill();
            int waybill_id = waybill.getWaybill_id() + 1;

            //conditionΪ0
            int condition = 0;

            //������ʽΪ�ָ�����˫����mark��Ϊ1
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
            params.put("random", random);

            waybillMapper.addWaybill(params);
            Waybill waybill1 = waybillMapper.getWaybillByWaybillId(waybill_id);

            Map params3 = new HashMap();
            params3.put("clientWaybill_id", clientWaybill_id);
            params3.put("user_id", user_id);
            params3.put("client_id", client_id);
            params3.put("waybill_id", waybill_id);
            clientWaybillMapper.updateClientWaybillCondition(params3);
            /**
             * ������ָ����ӣ��Զ����˷�
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
     * �ӵ�
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/receiveClientWaybill1", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String receiveClientWaybill1(HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int clientWaybill_id = Integer.parseInt(request.getParameter("clientWaybill_id"));

        ClientWaybill clientWaybill = clientWaybillMapper.getClientWaybillById(clientWaybill_id);
        if (clientWaybill.getCondition() >= 1) {
            ReturnResult returnResult = new ReturnResult(1);
            return ReturnResultUtil.ReturnResultToJSON(returnResult);
        }

        Map params = new HashMap();
        params.put("user_id", user_id);
        params.put("clientWaybill_id", clientWaybill_id);

        clientWaybillMapper.updateClientWaybillCondition1(params);

        ReturnResult returnResult = new ReturnResult(0);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

}
