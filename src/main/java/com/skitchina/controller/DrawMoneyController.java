package com.skitchina.controller;

import com.skitchina.mapper.ClientMapper;
import com.skitchina.mapper.DrawMoneyMapper;
import com.skitchina.model.DrawMoney;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/26.
 */
@Controller
@RequestMapping(value = "/drawMoney")
public class DrawMoneyController {

    @Autowired
    @Qualifier("drawMoneyMapperImpl")
    private DrawMoneyMapper drawMoneyMapper;

    @Autowired
    @Qualifier("clientMapperImpl")
    private ClientMapper clientMapper;

    /**
     * 获取提款申请
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/getDrawMoneys", method = RequestMethod.GET)
    public void getDrawMoneys(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int pages = Integer.parseInt(request.getParameter("pages"));
        int rows = 10;
        int m = (pages - 1) * 10;

        Map params = new HashMap();
        params.put("m", m);
        params.put("rows", rows);
        List<DrawMoney> drawMoneys = drawMoneyMapper.getDrawMoneys(params);

        int drawMoneysNum = drawMoneyMapper.getDrawMoneysNum();

        request.getSession().setAttribute("drawMoneys", drawMoneys);
        request.getSession().setAttribute("pagesNow", pages);
        request.getSession().setAttribute("drawMoneysNum", drawMoneysNum);

        response.sendRedirect(request.getContextPath() + "/drawmoney.jsp");
    }

    /**
     * 处理提现申请
     *
     * @param request
     */
    @RequestMapping(value = "/dealDrawMoneyById", method = RequestMethod.GET)
    public void dealDrawMoneyById(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int pagesNow = Integer.parseInt(request.getParameter("pagesNow"));
        drawMoneyMapper.updateDrawCondition(id);

        response.sendRedirect(request.getContextPath() + "/drawMoney/getDrawMoneys?pages=" + pagesNow);
    }

    /**
     * 提现不通过
     */
    @RequestMapping(value = "/errorDrawMoney", method = RequestMethod.GET)
    public void errorDrawMoney(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int pagesNow = Integer.parseInt(request.getParameter("pagesNow"));

        DrawMoney drawMoney = drawMoneyMapper.getDrawMoneyById(id);
        drawMoneyMapper.errorDrawMoney(id);

        Map params = new HashMap();
        params.put("money", -drawMoney.getMoney());
        params.put("id", drawMoney.getClient_id());
        clientMapper.updateBalance(params);

        request.getSession().setAttribute("pagesNow", pagesNow);

        response.sendRedirect(request.getContextPath() + "/drawMoney/getDrawMoneys?pages=" + pagesNow);
    }
}
