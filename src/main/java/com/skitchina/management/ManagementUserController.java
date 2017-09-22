package com.skitchina.management;

import com.skitchina.mapper.*;
import com.skitchina.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hu meng on 2017/4/26.
 */
@Controller
@RequestMapping(value = "/management")
public class ManagementUserController {

    @Autowired
    @Qualifier("managementMapperImpl")
    private ManagementMapper managementMapper;

    @Autowired
    @Qualifier("clientMapperImpl")
    private ClientMapper clientMapper;

    @Autowired
    @Qualifier("noticeMapperImpl")
    private NoticeMapper noticeMapper;

    @Autowired
    @Qualifier("advertiseMapperImpl")
    private AdvertisementMapper advertisementMapper;

    @Autowired
    @Qualifier("questionMapperImpl")
    private QuestionMapper questionMapper;

    @Autowired
    @Qualifier("completeWaybillMapperImpl")
    private CompleteWaybillMapper completeWaybillMapper;

    @Autowired
    @Qualifier("waybillMapperImpl")
    private WaybillMapper waybillMapper;

    /**
     * 用户登陆
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        System.out.println("username为："+username);
        String password = request.getParameter("password");
        System.out.println("password为："+password);
        if (username.equals("admin") && password.equals("456789")) {
            //1为登陆成功
            request.getSession().setAttribute("state", 1);
            response.sendRedirect(request.getContextPath()+"/management.jsp");
        } else {
            if (!username.equals("admin")) {
                //2为帐号错误
                request.getSession().setAttribute("state",2);
            } else if (!password.equals("456789")) {
                //3为密码错误
                request.getSession().setAttribute("state",3);
            }
        }
    }

    /**
     * 根据ID删除订单
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/deleteWaybillById", method = RequestMethod.GET)
    public void deleteWaybillById(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int id = Integer.parseInt(request.getParameter("id"));
        int type = Integer.parseInt(request.getParameter("type"));
        String consignor_company = request.getParameter("consignor_company");
        String consignee_company = request.getParameter("consignee_company");
        Waybill waybill = managementMapper.getWaybillById(id);
        managementMapper.deleteWaybillById(id);
        completeWaybillMapper.deleteCompleteWaybillByWaybillId(waybill.getWaybill_id());

        String url = null;
        String consignor_company2 = java.net.URLEncoder.encode(consignor_company, "UTF-8");
        String consignee_company2 = java.net.URLEncoder.encode(consignee_company, "UTF-8");
        if (type == 0) {
            url = request.getContextPath() + "/management/getWaybills?pages=" + pages + "&rows=10";
        } else if (type == 4) {
            url = request.getContextPath() + "/management/getWaybillByWaybillId?waybill_id2="+waybill.getWaybill_id();
        } else if (type == 1) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorCompany?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2;
        } else if (type == 2) {
            url = request.getContextPath() + "/management/getWaybillsByConsigneeCompany?pages=" + pages + "&rows=10&consigneeCompany=" + consignee_company2;
        } else if (type == 3) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorAndConsignee?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2 + "&consigneeCompany=" + consignee_company2;
        } else if (type == 5) {
            url = request.getContextPath() + "/management/getWaybillsNotSubmit?pages=" + pages + "&rows=10";
        }
        response.sendRedirect(url);
    }

    /**
     * 修改订单状态
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/updateCondition", method = RequestMethod.POST)
    public void updateCondition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int id = Integer.parseInt(request.getParameter("id"));
        int condition = Integer.parseInt(request.getParameter("condition"));
        int type = Integer.parseInt(request.getParameter("type"));
        int waybill_id = Integer.parseInt(request.getParameter("waybill_id"));
        String consignor_company = request.getParameter("consignor_company");
        String consignee_company = request.getParameter("consignee_company");

        Map params = new HashMap();
        params.put("id", id);
        params.put("condition", condition);

        managementMapper.updateCondition(params);

        if (condition == 5) {
            completeWaybillMapper.addCompleteWaybill(waybill_id);
        }

        String url = null;
        String consignor_company2 = java.net.URLEncoder.encode(consignor_company, "UTF-8");
        String consignee_company2 = java.net.URLEncoder.encode(consignee_company, "UTF-8");
        if (type == 0) {
            url = request.getContextPath() + "/management/getWaybills?pages=" + pages + "&rows=10";
        } else if (type == 4) {
            url = request.getContextPath() + "/management/getWaybillByWaybillId?waybill_id2="+waybill_id;
        } else if (type == 1) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorCompany?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2;
        } else if (type == 2) {
            url = request.getContextPath() + "/management/getWaybillsByConsigneeCompany?pages=" + pages + "&rows=10&consigneeCompany=" + consignee_company2;
        } else if (type == 3) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorAndConsignee?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2 + "&consigneeCompany=" + consignee_company2;
        } else if (type == 5) {
            url = request.getContextPath() + "/management/getWaybillsNotSubmit?pages=" + pages + "&rows=10";
        }
        response.sendRedirect(url);
    }

    /**
     * 用户管理
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);

        List<User> users = managementMapper.getUsers(params);
        List<Station> stations = managementMapper.getAllStations();
        int userNum = managementMapper.getUserNum();
        int pagesAll = userNum / rows;
        request.getSession().setAttribute("users", users);
        request.getSession().setAttribute("pagesAll",pagesAll);
        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("userNum", userNum);
        request.getSession().setAttribute("rows", rows);
        request.getSession().setAttribute("stations",stations);

        response.sendRedirect(request.getContextPath() + "/users.jsp");
    }

    /**
     * 修改权限
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/updatePower", method = RequestMethod.POST)
    public void updatePower(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int power = Integer.parseInt(request.getParameter("power"));
        int pages = Integer.parseInt(request.getParameter("pages"));

        Map params = new HashMap();
        params.put("id", id);
        params.put("power", power);

        managementMapper.updatePower(params);

        response.sendRedirect(request.getContextPath()+"/management/getAllUsers?pages="+pages+"&rows=10");
    }

    /**
     * 根据ID删除用户
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/deleteUserById", method = RequestMethod.GET)
    public void deleteUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int pages = Integer.parseInt(request.getParameter("pages"));

        managementMapper.deleteUserById(id);
        response.sendRedirect(request.getContextPath()+"/management/getAllUsers?pages="+pages+"&rows=10");
    }

    /**
     * 根据ID修改网点
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/updateStation", method = RequestMethod.POST)
    public void updateStation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int id = Integer.parseInt(request.getParameter("id"));
        String station = request.getParameter("station");

        Map params = new HashMap();
        params.put("id", id);
        params.put("station", station);
        managementMapper.updateStation(params);

        response.sendRedirect(request.getContextPath()+"/management/getAllUsers?pages="+pages+"&rows=10");
    }

    /**
     * 搜索用户
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getUserByCellphone", method = RequestMethod.POST)
    public void getUserByCellphone(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String cellphone = request.getParameter("cellphone");
        List<User> users = new ArrayList<User>();
        List<Station> stations = managementMapper.getAllStations();
        User user = managementMapper.getUserByCellphone(cellphone);
        users.add(user);

        request.getSession().setAttribute("users",users);
        request.getSession().setAttribute("stations", stations);
        request.getSession().setAttribute("pagesAll",0);
        request.getSession().setAttribute("pagesNow",1);
        request.getSession().setAttribute("userNum",1);
        request.getSession().setAttribute("rows",10);
        response.sendRedirect(request.getContextPath() + "/users.jsp");
    }

    /**
     * 网点管理
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getAllStations", method = RequestMethod.GET)
    public void getAllStations(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Station> stations = managementMapper.getAllStations();

        request.getSession().setAttribute("stations", stations);
        request.getSession().setAttribute("pagesAll",0);
        request.getSession().setAttribute("pagesNow",1);
        request.getSession().setAttribute("rows",10);

        response.sendRedirect(request.getContextPath() + "/stations.jsp");
    }

    /**
     * 根据ID删除网点
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteStationById", method = RequestMethod.GET)
    public void deleteStationById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        managementMapper.deleteStationById(id);

        response.sendRedirect(request.getContextPath()+"/management/getAllStations");
    }

    /**
     * 增加网点
     * @param request
     * @param response
     */
    @RequestMapping(value = "/addStation", method = RequestMethod.POST)
    public void addStation(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String station = request.getParameter("station");
        List<Station> stations = managementMapper.getAllStations();
        String arrive = "";
        for (Station station1 : stations) {
            arrive = arrive + station1.getId() + ",";
        }
        Map params = new HashMap();
        params.put("name", station);
        params.put("arrive", arrive);

        managementMapper.addStation(params);
        int id = managementMapper.getMaxStationId();

        for (Station station1 : stations) {
            String arrive1 = station1.getArrive() + id + ",";
            Map params1 = new HashMap();
            params1.put("arrive", arrive1);
            params1.put("id", station1.getId());
            managementMapper.updateArriveById(params1);
        }

        System.out.println(id);
        response.sendRedirect(request.getContextPath()+"/management/getAllStations");
    }

    /**
     * 修改waybill
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateWaybillByWaybillId", method = RequestMethod.POST)
    public void updateWaybillById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int type = Integer.parseInt(request.getParameter("type"));
        int pages = Integer.parseInt(request.getParameter("pagesNow"));
        int waybill_id = Integer.parseInt(request.getParameter("waybill_id"));
        String consignor_company = request.getParameter("consignor_company1").trim();
        String consignor_tel = request.getParameter("consignor_tel").trim();
        String origin = request.getParameter("origin").trim();
        String consignor_address = request.getParameter("consignor_address").trim();
        String consignee_company = request.getParameter("consignee_company1").trim();
        String consignee_tel = request.getParameter("consignee_tel").trim();
        String destination = request.getParameter("destination").trim();
        String consignee_address = request.getParameter("consignee_address").trim();
        String remark = request.getParameter("remark").trim();
        double price = Double.parseDouble(request.getParameter("price"));
        double freight = Double.parseDouble(request.getParameter("freight"));
        int number = Integer.parseInt(request.getParameter("number"));

        Map params = new HashMap();
        params.put("waybill_id", waybill_id);
        params.put("consignor_company", consignor_company);
        params.put("consignor_tel", consignor_tel);
        params.put("origin", origin);
        params.put("consignor_address", consignor_address);
        params.put("consignee_company", consignee_company);
        params.put("consignee_tel", consignee_tel);
        params.put("destination", destination);
        params.put("consignee_address", consignee_address);
        params.put("price", price);
        params.put("freight", freight);
        params.put("number", number);
        params.put("remark", remark);

        managementMapper.updateWaybillByWaybillId(params);

        String url = null;
        String consignor_company2 = java.net.URLEncoder.encode(consignor_company, "UTF-8");
        String consignee_company2 = java.net.URLEncoder.encode(consignee_company, "UTF-8");
        if (type == 0) {
            url = request.getContextPath() + "/management/getWaybills?pages=" + pages + "&rows=10";
        } else if (type == 4) {
            url = request.getContextPath() + "/management/getWaybillByWaybillId?waybill_id2="+waybill_id;
        } else if (type == 1) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorCompany?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2;
        } else if (type == 2) {
            url = request.getContextPath() + "/management/getWaybillsByConsigneeCompany?pages=" + pages + "&rows=10&consigneeCompany=" + consignee_company2;
        } else if (type == 3) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorAndConsignee?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2 + "&consigneeCompany=" + consignee_company2;
        } else if (type == 5) {
            url = request.getContextPath() + "/management/getWaybillsNotSubmit?pages=" + pages + "&rows=10";
        }
        response.sendRedirect(url);
    }

    /**
     * 作废订单
     * @param request
     * @param response
     */
    @RequestMapping(value = "/invalidWaybillById", method = RequestMethod.GET)
    public void invalidWaybillById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int type = Integer.parseInt(request.getParameter("type"));
        String consignor_company = request.getParameter("consignor_company");
        String consignee_company = request.getParameter("consignee_company");
        Waybill waybill = managementMapper.getWaybillById(id);
        managementMapper.invalidWaybillById(id);

        String url = null;
        String consignor_company2 = java.net.URLEncoder.encode(consignor_company, "UTF-8");
        String consignee_company2 = java.net.URLEncoder.encode(consignee_company, "UTF-8");
        if (type == 0) {
            url = request.getContextPath() + "/management/getWaybills?pages=" + pages + "&rows=10";
        } else if (type == 4) {
            url = request.getContextPath() + "/management/getWaybillByWaybillId?waybill_id2="+waybill.getWaybill_id();
        } else if (type == 1) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorCompany?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2;
        } else if (type == 2) {
            url = request.getContextPath() + "/management/getWaybillsByConsigneeCompany?pages=" + pages + "&rows=10&consigneeCompany=" + consignee_company2;
        } else if (type == 3) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorAndConsignee?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2 + "&consigneeCompany=" + consignee_company2;
        } else if (type == 5) {
            url = request.getContextPath() + "/management/getWaybillsNotSubmit?pages=" + pages + "&rows=10";
        }
        response.sendRedirect(url);
    }

    /**
     * 修改用户
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateUserById", method = RequestMethod.GET)
    public void updateUserById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pagesNow"));
        int id = Integer.parseInt(request.getParameter("user_id"));
        String name = request.getParameter("name");
        String cellphone = request.getParameter("cellphone");
        String password = request.getParameter("password");
        String company_name = request.getParameter("company_name");
        String company_tel = request.getParameter("company_tel");
        String company_address = request.getParameter("company_address");
        double achievement = Double.parseDouble(request.getParameter("achievement"));

        Map params = new HashMap();
        params.put("id", id);
        params.put("name", name);
        params.put("cellphone", cellphone);
        params.put("password", password);
        params.put("company_name", company_name);
        params.put("company_tel", company_tel);
        params.put("company_address", company_address);
        params.put("achievement", achievement);

        managementMapper.updateUserById(params);

        response.sendRedirect(request.getContextPath()+"/management/getAllUsers?pages="+pages+"&rows=10");
    }

    /**
     * 根据consignor_company搜索订单
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillsByConsignorCompany", method = RequestMethod.GET)
    public void getWaybillsByConsignorCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        String consignor_company = request.getParameter("consignorCompany");
        int rows = Integer.parseInt(request.getParameter("rows"));

        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);
        params.put("consignor_company", consignor_company);
        List<Waybill> waybills = managementMapper.getWaybillsByConsignorCompany(params);

        int waybillNum = managementMapper.getWaybillsByConsignorCompanyNum(params);
        int pagesAll = waybillNum / rows;

        List<User> users = new ArrayList<User>();
        for (Waybill waybill : waybills) {
            User user2 = managementMapper.getUserById(waybill.getUser_id());
            if (user2 != null) {
                users.add(user2);
            }
        }
        Map users1 = new HashMap();
        for (User user : users) {
            users1.put(user.getId(), user.getName());
        }

        request.getSession().setAttribute("consignor_company",consignor_company);
        request.getSession().setAttribute("consignee_company","no");
        request.getSession().setAttribute("type",1);
        request.getSession().setAttribute("waybills",waybills);
        request.getSession().setAttribute("users", users1);
        request.getSession().setAttribute("pagesAll",pagesAll);
        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("waybillNum",waybillNum);
        request.getSession().setAttribute("rows",rows);
        response.sendRedirect(request.getContextPath() + "/waybills.jsp");
    }

    /**
     * 根据consignee_company搜索
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillsByConsigneeCompany", method = RequestMethod.GET)
    public void getWaybillsByConsigneeCompany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;
        String consignee_company = request.getParameter("consigneeCompany");
        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);
        params.put("consignee_company", consignee_company);
        List<Waybill> waybills = managementMapper.getWaybillsByConsigneeCompany(params);
        int waybillNum = managementMapper.getWaybillsByConsigneeCompanyNum(params);
        int pagesAll = waybillNum / rows;

        List<User> users = new ArrayList<User>();
        for (Waybill waybill : waybills) {
            User user2 = managementMapper.getUserById(waybill.getUser_id());
            if (user2 != null) {
                users.add(user2);
            }
        }
        Map users1 = new HashMap();
        for (User user : users) {
            users1.put(user.getId(), user.getName());
        }

        request.getSession().setAttribute("consignor_company","no");
        request.getSession().setAttribute("consignee_company",consignee_company);
        request.getSession().setAttribute("type",2);
        request.getSession().setAttribute("waybills",waybills);
        request.getSession().setAttribute("users", users1);
        request.getSession().setAttribute("pagesAll",pagesAll);
        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("waybillNum",waybillNum);
        request.getSession().setAttribute("rows",rows);
        response.sendRedirect(request.getContextPath() + "/waybills.jsp");
    }

    /**
     * 根据consignor_company和consignee_company搜索
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillsByConsignorAndConsignee", method = RequestMethod.GET)
    public void getWaybillsByConginorAndConsignee(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;
        String consignor_company = request.getParameter("consignorCompany");
        String consignee_company = request.getParameter("consigneeCompany");
        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);
        params.put("consignor_company", consignor_company);
        params.put("consignee_company", consignee_company);
        List<Waybill> waybills = managementMapper.getWaybillsByConsignorAndConsignee(params);

        int waybillNum = managementMapper.getWaybillsByConsignorAndConsigneeNum(params);
        int pagesAll = waybillNum / rows;

        List<User> users = new ArrayList<User>();
        for (Waybill waybill : waybills) {
            User user2 = managementMapper.getUserById(waybill.getUser_id());
            if (user2 != null) {
                users.add(user2);
            }
        }
        Map users1 = new HashMap();
        for (User user : users) {
            users1.put(user.getId(), user.getName());
        }

        request.getSession().setAttribute("consignor_company",consignor_company);
        request.getSession().setAttribute("consignee_company",consignee_company);
        request.getSession().setAttribute("type",3);
        request.getSession().setAttribute("waybills",waybills);
        request.getSession().setAttribute("users", users1);
        request.getSession().setAttribute("pagesAll",pagesAll);
        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("waybillNum",waybillNum);
        request.getSession().setAttribute("rows",rows);
        response.sendRedirect(request.getContextPath() + "/waybills.jsp");
    }

    /**
     * 订单管理
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/getWaybills",method = RequestMethod.GET)
    public void getWaybills(HttpServletRequest request,HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = Integer.parseInt(request.getParameter("rows"));
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);

        List<Waybill> waybills = managementMapper.getAllWaybills(params);
        List<User> users = new ArrayList<User>();

        for (Waybill waybill :waybills) {
            User user = managementMapper.getUserById(waybill.getUser_id());
            if (user != null) {
                users.add(user);
            }
        }
        int waybillNum = managementMapper.getWaybillNum();
        int pagesAll = waybillNum/rows;
        Map users1 = new HashMap();
        for (User user : users) {
            users1.put(user.getId(), user.getName());
        }
        request.getSession().setAttribute("consignor_company","no");
        request.getSession().setAttribute("consignee_company","no");
        request.getSession().setAttribute("type",0);
        request.getSession().setAttribute("waybills",waybills);
        request.getSession().setAttribute("users", users1);
        request.getSession().setAttribute("pagesAll",pagesAll);
        request.getSession().setAttribute("pagesNow",pages);
        System.out.println("=============================pagesNow="+pages);
        request.getSession().setAttribute("waybillNum",waybillNum);
        System.out.println("=============================waybillNum="+waybillNum);
        request.getSession().setAttribute("rows",rows);
        response.sendRedirect(request.getContextPath() + "/waybills.jsp");
    }

    /**
     * 根据waybill_id搜索
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillByWaybillId", method = RequestMethod.GET)
    public void getWaybillById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Waybill> waybills = new ArrayList<Waybill>();
        List<User> users = new ArrayList<User>();
        Map users1 = new HashMap();
        int waybill_id = Integer.parseInt(request.getParameter("waybill_id2"));
        Waybill waybill = managementMapper.getWaybillByWaybillId(waybill_id);
        if (waybill != null) {
            User user = managementMapper.getUserById(waybill.getUser_id());
            waybills.add(waybill);
            users.add(user);
            for (User user2 : users) {
                users1.put(user2.getId(), user2.getName());
            }
        } else {
            users1.put("", "");
            Waybill waybill1 = new Waybill();
            waybill1.setWaybill_id(0);
            waybills.add(waybill1);
        }
        request.getSession().setAttribute("waybill_id", waybill_id);
        request.getSession().setAttribute("consignor_company","no");
        request.getSession().setAttribute("consignee_company","no");
        request.getSession().setAttribute("type",4);
        request.getSession().setAttribute("waybills",waybills);
        request.getSession().setAttribute("users",users1);
        request.getSession().setAttribute("pagesAll",0);
        request.getSession().setAttribute("pagesNow",1);
        request.getSession().setAttribute("waybillNum",1);
        request.getSession().setAttribute("rows",10);
        response.sendRedirect(request.getContextPath() + "/waybills.jsp");
    }

    /**
     * 修改支付方式
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updatePayway", method = RequestMethod.GET)
    public void updatePayway(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String consignor_company = request.getParameter("consignor_company");
        String consignee_company = request.getParameter("consignee_company");
        int type = Integer.parseInt(request.getParameter("type"));
        int pages = Integer.parseInt(request.getParameter("pagesNow"));
        int waybill_id = Integer.parseInt(request.getParameter("waybill_id"));
        Waybill waybill = managementMapper.getWaybillByWaybillId(waybill_id);
        if (waybill.getPayway() == 0) {
            Map params = new HashMap();
            params.put("waybill_id", waybill_id);
            params.put("payway", 1);
            managementMapper.updatePayway(params);
        } else if (waybill.getPayway() == 1) {
            Map params = new HashMap();
            params.put("waybill_id", waybill_id);
            params.put("payway", 0);
            managementMapper.updatePayway(params);
        }
        String url = null;
        String consignor_company2 = java.net.URLEncoder.encode(consignor_company, "UTF-8");
        String consignee_company2 = java.net.URLEncoder.encode(consignee_company, "UTF-8");
        if (type == 0) {
            url = request.getContextPath() + "/management/getWaybills?pages=" + pages + "&rows=10";
        } else if (type == 4) {
            url = request.getContextPath() + "/management/getWaybillByWaybillId?waybill_id2="+waybill_id;
        } else if (type == 1) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorCompany?consignorCompany="+consignor_company2+"&pages=" + pages + "&rows=10";
        } else if (type == 2) {
            url = request.getContextPath() + "/management/getWaybillsByConsigneeCompany?pages=" + pages + "&rows=10&consigneeCompany=" + consignee_company2;
        } else if (type == 3) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorAndConsignee?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2 + "&consigneeCompany=" + consignee_company2;
        } else if (type == 5) {
            url = request.getContextPath() + "/management/getWaybillsNotSubmit?pages=" + pages + "&rows=10";
        }
        response.sendRedirect(url);
    }

    /**
     * 修改状态为结束
     * @param request
     * @param response
     */
    @RequestMapping(value = "endWaybill", method = RequestMethod.GET)
    public void endWaybill(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int type = Integer.parseInt(request.getParameter("type"));
        String consignor_company = request.getParameter("consignor_company");
        String consignee_company = request.getParameter("consignee_company");
        int pages = Integer.parseInt(request.getParameter("pagesNow"));
        int id = Integer.parseInt(request.getParameter("id"));
        Waybill waybill = managementMapper.getWaybillById(id);
        managementMapper.endWaybill(id);

        String url = null;
        String consignor_company2 = java.net.URLEncoder.encode(consignor_company, "UTF-8");
        String consignee_company2 = java.net.URLEncoder.encode(consignee_company, "UTF-8");
        if (type == 0) {
            url = request.getContextPath() + "/management/getWaybills?pages=" + pages + "&rows=10";
        } else if (type == 4) {
            url = request.getContextPath() + "/management/getWaybillByWaybillId?waybill_id2="+waybill.getWaybill_id();
        } else if (type == 1) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorCompany?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2;
        } else if (type == 2) {
            url = request.getContextPath() + "/management/getWaybillsByConsigneeCompany?pages=" + pages + "&rows=10&consigneeCompany=" + consignee_company2;
        } else if (type == 3) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorAndConsignee?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2 + "&consigneeCompany=" + consignee_company2;
        } else if (type == 5) {
            url = request.getContextPath() + "/management/getWaybillsNotSubmit?pages=" + pages + "&rows=10";
        }
        response.sendRedirect(url);
    }

    /**
     * 根据ID修改运费或者代收款
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateFreightOrPrice", method = RequestMethod.GET)
    public void updateFreightOrPrice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入到修改运费controller");
        int type = Integer.parseInt(request.getParameter("type"));
        String consignor_company = request.getParameter("consignor_company");
        String consignee_company = request.getParameter("consignee_company");
        int pages = Integer.parseInt(request.getParameter("pages"));
        double freight = Double.parseDouble(request.getParameter("freight"));
        double price = Double.parseDouble(request.getParameter("price"));
        int id = Integer.parseInt(request.getParameter("id"));
        Waybill waybill = managementMapper.getWaybillById(id);

        Map params = new HashMap();
        params.put("freight", freight);
        params.put("price", price);
        params.put("id", id);

        managementMapper.updateFreightOrPrice(params);

        String url = null;
        String consignor_company2 = java.net.URLEncoder.encode(consignor_company, "UTF-8");
        String consignee_company2 = java.net.URLEncoder.encode(consignee_company, "UTF-8");
        if (type == 0) {
            url = request.getContextPath() + "/management/getWaybills?pages=" + pages + "&rows=10";
        } else if (type == 4) {
            url = request.getContextPath() + "/management/getWaybillByWaybillId?waybill_id2="+waybill.getWaybill_id();
        } else if (type == 1) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorCompany?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2;
        } else if (type == 2) {
            url = request.getContextPath() + "/management/getWaybillsByConsigneeCompany?pages=" + pages + "&rows=10&consigneeCompany=" + consignee_company2;
        } else if (type == 3) {
            url = request.getContextPath() + "/management/getWaybillsByConsignorAndConsignee?pages=" + pages + "&rows=10&consignorCompany=" + consignor_company2 + "&consigneeCompany=" + consignee_company2;
        } else if (type == 5) {
            url = request.getContextPath() + "/management/getWaybillsNotSubmit?pages=" + pages + "&rows=10";
        }
        response.sendRedirect(url);
    }

    /**
     * 查询未交账的运单
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillsNotSubmit", method = RequestMethod.GET)
    public void getWaybillsNotSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * rows;
        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);
        Map users1 = new HashMap();
        List<Waybill> waybills = managementMapper.getWaybillsNotSubmit(params);

        int waybillNum = managementMapper.getWaybillNotSubmitNum();
        int pagesAll = waybillNum / 10;
        for (Waybill waybill : waybills) {
            User user = managementMapper.getUserById(waybill.getUser_id());
            users1.put(waybill.getUser_id(), user.getName());
        }

        request.getSession().setAttribute("consignor_company","no");
        request.getSession().setAttribute("consignee_company","no");
        request.getSession().setAttribute("type",5);
        request.getSession().setAttribute("waybills",waybills);
        request.getSession().setAttribute("users", users1);
        request.getSession().setAttribute("pagesAll",pagesAll);
        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("waybillNum",waybillNum);
        request.getSession().setAttribute("rows",rows);
        response.sendRedirect(request.getContextPath() + "/waybills.jsp");
    }

    /**
     * 查询已收账未交账的运单
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillsReceiveAndNotSubmit",method = RequestMethod.GET)
    public void getWaybillsReceiveAndNotSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * 10;
        Map params = new HashMap();
        params.put("rows", rows);
        params.put("m", m);
        List<Waybill> waybills = managementMapper.getWaybillsReceiveAndNotSubmit(params);
        Map users1 = new HashMap();
        for (Waybill waybill : waybills) {
            User user = managementMapper.getUserById(waybill.getUser_id());
            users1.put(waybill.getUser_id(), user.getName());
        }
        int waybillNum = managementMapper.getWaybillsReceiveAndNotSubmitNum();

        request.getSession().setAttribute("consignor_company","no");
        request.getSession().setAttribute("consignee_company","no");
        request.getSession().setAttribute("type",6);
        request.getSession().setAttribute("waybills",waybills);
        request.getSession().setAttribute("users", users1);
        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("waybillNum",waybillNum);
        request.getSession().setAttribute("rows",rows);
        response.sendRedirect(request.getContextPath() + "/waybills.jsp");
    }

    /**
     * 财务管理
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getAllSubmits", method = RequestMethod.GET)
    public void getAllSubmits(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * rows;
        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);
        List<Submit> submits = managementMapper.getAllSubmits(params);
        int submitsNum = managementMapper.getSubmitsNum();

        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("submits", submits);
        request.getSession().setAttribute("submitsNum", submitsNum);

        response.sendRedirect(request.getContextPath()+"/submits.jsp");
    }

    /**
     * 财务管理 查看详情
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillsBySubmit", method = RequestMethod.GET)
    public void getWaybillsBySubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages1 = Integer.parseInt(request.getParameter("pagesOnSubmit"));
        int pages2 = Integer.parseInt(request.getParameter("pages"));
        int id = Integer.parseInt(request.getParameter("id"));

        Submit submit = managementMapper.getSubmitById(id);

        String freight = submit.getFreight();
        String price = submit.getPrice();
        String freightAndPrice = submit.getFreightandprice();

        String[] freight_ids, price_ids, freightAndPrice_ids;

        List<Waybill> waybills = new ArrayList<Waybill>();

        if (!freight.equals("no")) {
            freight_ids = freight.split(",");
            for (int i=0;i<freight_ids.length;i++) {
                Waybill waybill = managementMapper.getWaybillById(Integer.parseInt(freight_ids[i]));
                waybills.add(waybill);
            }
        }

        if (!price.equals("no")) {
            price_ids = price.split(",");
            for (int i=0;i<price_ids.length;i++) {
                Waybill waybill = managementMapper.getWaybillById(Integer.parseInt(price_ids[i]));
                waybills.add(waybill);
            }
        }

        if (!freightAndPrice.equals("no")) {
            freightAndPrice_ids = freightAndPrice.split(",");
            for (int i=0;i<freightAndPrice_ids.length;i++) {
                Waybill waybill = managementMapper.getWaybillById(Integer.parseInt(freightAndPrice_ids[i]));
                waybills.add(waybill);
            }
        }
        int waybillNum = waybills.size();
        System.out.println("======waybills里有"+waybillNum+"个元素");
        if (waybills.size() > 10) {
            if (pages2 == 1) {
                for (int i=10;i<waybillNum;i++) {
                    waybills.remove(10);
                }
            } else if (pages2 > 1) {
                int head = (pages2 - 1)*10;
                for (int i=0;i<head;i++) {
                    waybills.remove(0);
                    if (waybills.size() == 10) {
                        break;
                    }
                }
                if (waybills.size()!=10) {
                    for (int i = 10; i < waybillNum; i++) {
                        waybills.remove(10);
                        if (waybills.size() == 10) {
                            break;
                        }
                    }
                }
            }
        }
        Map users = new HashMap();
        for (Waybill waybill : waybills) {
            User user = managementMapper.getUserById(waybill.getUser_id());
            users.put(waybill.getUser_id(), user.getName());
        }

        request.getSession().setAttribute("id", id);
        request.getSession().setAttribute("pagesNow",pages2);
        request.getSession().setAttribute("users", users);
        request.getSession().setAttribute("waybillNum",waybillNum);
        request.getSession().setAttribute("waybills", waybills);
        request.getSession().setAttribute("pagesOnSubmit",pages1);

        response.sendRedirect(request.getContextPath()+"/waybillsbysubmit.jsp");

    }

    /**
     * 获取提交对账记录
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getCheckSubmits", method = RequestMethod.GET)
    public void getCheckSubmits(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);

        int checkSubmitsNum = managementMapper.getCheckSubmitsNum();
        List<CheckSubmit> checkSubmits = managementMapper.getCheckSubmits(params);

        Map clients = new HashMap();
        for (CheckSubmit checkSubmit : checkSubmits) {
            clients.put(checkSubmit.getClient_id(), clientMapper.getClientById(checkSubmit.getClient_id()).getName());
        }

        request.getSession().setAttribute("clients", clients);
        request.getSession().setAttribute("checkSubmits", checkSubmits);
        request.getSession().setAttribute("pagesNow", pages);
        request.getSession().setAttribute("checkSubmitsNum",checkSubmitsNum);

        response.sendRedirect(request.getContextPath()+"/checksubmits.jsp");
    }

    /**
     * 根据checksubmit获取waybill
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getWaybillsByCheckSubmit", method = RequestMethod.GET)
    public void getWaybillsByCheckSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pagesOnCheckSubmits = Integer.parseInt(request.getParameter("pagesOnCheckSubmits"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int checksubmit_id = Integer.parseInt(request.getParameter("id"));

        CheckSubmit checkSubmit = clientMapper.getCheckSubmitById(checksubmit_id);

        String waybill_ids = checkSubmit.getWaybill_ids();
        String[] waybill_ids2 = waybill_ids.split(",");
        List<Waybill> waybills = new ArrayList<Waybill>();
        boolean a = true;
        for (int i=0;i<waybill_ids2.length;i++) {
            Waybill waybill = managementMapper.getWaybillByWaybillId(Integer.parseInt(waybill_ids2[i]));
            if (waybill.getCheck_condition() == 2) {
                a = false;
            }
            waybills.add(waybill);
        }
        if (a) {
            clientMapper.updateCheckSubmitCondition(checksubmit_id);
        }
        int waybillNum = waybills.size();
        if (waybills.size() > 10) {
            if (pages == 1) {
                for (int i=10;i<waybillNum;i++) {
                    waybills.remove(10);
                }
            } else if (pages > 1) {
                int head = (pages - 1)*10;
                for (int i=0;i<head;i++) {
                    waybills.remove(0);
                    if (waybills.size() == 10) {
                        break;
                    }
                }
                if (waybills.size()!=10) {
                    for (int i = 10; i < waybillNum; i++) {
                        waybills.remove(10);
                        if (waybills.size() == 10) {
                            break;
                        }
                    }
                }
            }
        }

        Map users = new HashMap();
        for (Waybill waybill : waybills) {
            User user = managementMapper.getUserById(waybill.getUser_id());
            users.put(waybill.getUser_id(), user.getName());
        }

        request.getSession().setAttribute("id", checksubmit_id);
        request.getSession().setAttribute("pagesNow",pages);
        request.getSession().setAttribute("users", users);
        request.getSession().setAttribute("waybillNum",waybillNum);
        request.getSession().setAttribute("waybills", waybills);
        request.getSession().setAttribute("pagesOnCheckSubmits",pagesOnCheckSubmits);

        response.sendRedirect(request.getContextPath()+"/waybillsbychecksubmits.jsp");
    }

    /**
     * 对账通过审核
     * @param request
     * @param response
     */
    @RequestMapping(value = "/checkPass", method = RequestMethod.GET)
    public void checkPass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //此id为waybill表里面的主键id
        int id = Integer.parseInt(request.getParameter("waybillId"));
        int pages = Integer.parseInt(request.getParameter("pages"));
        int checkSubmitId = Integer.parseInt(request.getParameter("id"));
        int pagesOnCheckSubmits = Integer.parseInt(request.getParameter("pagesOnCheckSubmits"));

        clientMapper.updateCheckCondition3(id);

        Waybill waybill = managementMapper.getWaybillById(id);
        CheckSubmit checkSubmit = clientMapper.getCheckSubmitById(checkSubmitId);

        Map params = new HashMap();
        params.put("money", waybill.getPrice());
        params.put("id", checkSubmit.getClient_id());

        clientMapper.updateBalance(params);

        response.sendRedirect(request.getContextPath()+"/management/getWaybillsByCheckSubmit?pagesOnCheckSubmits="+pagesOnCheckSubmits+"&pages="+pages+"&id="+checkSubmitId);
    }

    /**
     * 获取所有公告
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getAllNotices", method = RequestMethod.GET)
    public void getAllNotices(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);

        List<Notice> notices = noticeMapper.getAllNotices(params);
        int noticesNum = noticeMapper.getNoticesNum();

        request.getSession().setAttribute("notices", notices);
        request.getSession().setAttribute("noticesNum", noticesNum);
        request.getSession().setAttribute("pagesNow", pages);

        response.sendRedirect(request.getContextPath()+"/notice.jsp");
    }

    /**
     * 删除notice
     * @param request
     * @param response
     */
    @RequestMapping(value = "/deleteNoticeById")
    public void deleteNoticeById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pagesNow"));
        int id = Integer.parseInt(request.getParameter("id"));

        noticeMapper.deleteNotice(id);

        response.sendRedirect(request.getContextPath()+"/management/getAllNotices?pages="+pages);
    }

    @RequestMapping(value = "addNotice")
    public void addNotice(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = request.getParameter("content");
        int pages = Integer.parseInt(request.getParameter("pagesNow"));

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("content", content);
        params.put("time", time);

        noticeMapper.addNotice(params);

        response.sendRedirect(request.getContextPath() + "/management/getAllNotices?pages=" + pages);
    }

    /**
     * 获取所有商家
     *
     * @param response
     */
    @RequestMapping(value = "/getAllClients")
    public void getAllClients(HttpServletRequest request, HttpServletResponse response) throws IOException {

        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);

        List<Advertisement> advertisementList = advertisementMapper.getAllAdvertisements();
        System.out.println("=====advertisementList.size=" + advertisementList.size());
        List<Client> clientList = new ArrayList<>();
        Map<Integer, Advertisement> map = new HashMap<>();
        for (Advertisement advertisement : advertisementList) {
            Client client = clientMapper.getClientById(advertisement.getClient_id());
            clientList.add(client);
            map.put(advertisement.getClient_id(), advertisement);
        }

        System.out.println("=====clientList.size=" + clientList.size());
        System.out.println("=====map.size=" + map.size());

        List<Client> clientList2 = new ArrayList<>();
        for (Client client : clientList) {
            if (map.get(client.getId()).getCondition() == 1) {
                clientList2.add(client);
            }
        }

        for (Client client : clientList2) {
            clientList.remove(client);
            clientList.add(client);
        }
        request.getSession().setAttribute("clientList", clientList);
        request.getSession().setAttribute("advertisements", map);
        request.getSession().setAttribute("clientsNum", clientMapper.getClientsNum());
        request.getSession().setAttribute("pagesNow", pages);

        response.sendRedirect(request.getContextPath()+"/clients.jsp");
    }

    /**
     * 展示商家
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/showClient")
    public void showClient(HttpServletRequest request, HttpServletResponse response) throws IOException{
        int client_id = Integer.parseInt(request.getParameter("client_id"));
        int pagesNow = Integer.parseInt(request.getParameter("pagesNow"));

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("client_id", client_id);
        params.put("show_time", time);

        advertisementMapper.updateCondition(params);

        response.sendRedirect(request.getContextPath() + "/management/getAllClients?pages=" + pagesNow);
    }

    /**
     * 隐藏商家
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/hideClient")
    public void hideClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int client_id = Integer.parseInt(request.getParameter("client_id"));
        int pagesNow = Integer.parseInt(request.getParameter("pagesNow"));

        advertisementMapper.updateCondition0(client_id);

        response.sendRedirect(request.getContextPath() + "/management/getAllClients?pages=" + pagesNow);
    }

    /**
     * 新增常见问题
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/addQuestion",method = RequestMethod.POST)
    public void addQuestion(HttpServletRequest request,HttpServletResponse response)throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(new Date());

        Map params = new HashMap();
        params.put("title", title);
        params.put("content", content);
        params.put("question_time", time);

        questionMapper.addQuestion(params);

        response.sendRedirect(request.getContextPath() + "/management/getAllQuestions");
    }

    /**
     * 获取常见问题
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/getAllQuestions", method = RequestMethod.GET)
    public void getAllQuestions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Question> questionList = questionMapper.getAllQuestions();
        request.getSession().setAttribute("questionsList", questionList);
        response.sendRedirect(request.getContextPath() + "/questions.jsp");
    }

    /**
     * 删除常见问题
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/deleteQuestion",method = RequestMethod.POST)
    public void deleteQuestion(HttpServletRequest request,HttpServletResponse response)throws IOException {
        int id = Integer.parseInt(request.getParameter("question_id"));
        questionMapper.deleteQuestion(id);
        response.sendRedirect(request.getContextPath() + "/management/getAllQuestions");
    }

    /**
     * 获取已完成的订单
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/getAllCompleteWaybills", method = RequestMethod.GET)
    public void getAllCompleteWaybills(HttpServletRequest request, HttpServletResponse response)throws IOException {

        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * rows;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", 10);

        List<CompleteWaybill> completeWaybillList = completeWaybillMapper.getAllCompleteWaybills(params);
        List<Waybill> waybillList = new ArrayList<>();
        Map users1 = new HashMap();

        for (CompleteWaybill completeWaybill : completeWaybillList) {
            Waybill waybill = waybillMapper.getWaybillByWaybill_id(completeWaybill.getWaybill_id());
            if (waybill!=null) {
                User user = managementMapper.getUserById(waybill.getUser_id());
                if (user != null) {
                    users1.put(user.getId(), user.getName());
                }
                waybillList.add(waybill);
            }
        }

        int waybillNum = completeWaybillMapper.getCompleteWaybillsNum();

        request.getSession().setAttribute("consignor_company","no");
        request.getSession().setAttribute("consignee_company","no");
        request.getSession().setAttribute("type",0);
        request.getSession().setAttribute("waybills",waybillList);
        request.getSession().setAttribute("users", users1);
        request.getSession().setAttribute("pagesNow",pages);
        System.out.println("=============================pagesNow="+pages);
        request.getSession().setAttribute("waybillNum",waybillNum);
        System.out.println("=============================waybillNum="+waybillNum);
        request.getSession().setAttribute("rows",rows);
        response.sendRedirect(request.getContextPath() + "/completewaybills.jsp");

    }

}


