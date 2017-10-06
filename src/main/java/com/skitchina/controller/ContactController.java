package com.skitchina.controller;

import com.skitchina.mapper.ContactMapper;
import com.skitchina.model.Contact;
import com.skitchina.model.ReturnResult;
import com.skitchina.utils.ReturnResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/18.
 */
@Controller
@RequestMapping(value = "/contact")
public class ContactController {

    @Autowired
    @Qualifier("contactMapperImpl")
    private ContactMapper contactMapper;

    @ResponseBody
    @RequestMapping(value = "/addContact",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String addContact(HttpServletRequest request) {

        int user_id = Integer.parseInt(request.getParameter("user_id"));
        String cellphone = request.getParameter("cellphone");
        String company = request.getParameter("company");
        String address = request.getParameter("address");

        Map params = new HashMap();

        params.put("user_id", user_id);
        params.put("cellphone", cellphone);
        params.put("company", company);
        params.put("address", address);

        contactMapper.addContact(params);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(1);
        returnResult.setMessage("添加成功");

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 查询常用联系人
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getContacts", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getContacts(HttpServletRequest request) {
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;
//        int user_id = Integer.parseInt(request.getParameter("user_id"));
        Map params = new HashMap();
//        params.put("user_id", user_id);
        params.put("value", value);
        params.put("m", m);
        params.put("rows", rows);
        List<Contact> contacts = new ArrayList<Contact>();

        if (key.equals("cellphone")) {
            contacts = contactMapper.getContactsByCellphone(params);
        } else if (key.equals("company")) {
            contacts = contactMapper.getContactsByCompany(params);
        }

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(contacts);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * 根据id删除contact
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteContactById",produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String deleteContactById(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        contactMapper.deleteContactById(id);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(1);
        returnResult.setMessage("删除成功");

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

}
