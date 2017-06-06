package com.skitchina.controller;

import com.skitchina.mapper.StationMapper;
import com.skitchina.model.ReturnResult;
import com.skitchina.model.Station;
import com.skitchina.utils.ReturnResultUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu meng on 2017/4/21.
 */
@Controller
@RequestMapping(value = "/station")
public class StationController {

    @Autowired
    private StationMapper stationMapper;

    /**
     * ��ȡ��������
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAllStations", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String getAllStations(HttpServletRequest request) {
        List<Station> stations = stationMapper.getAllStations();
        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(stations);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * ��ȡ��������Է�����������
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getArriveStations", produces = "application/json;charset=utf-8",method = RequestMethod.POST)
    public String getArriveStations(HttpServletRequest request) {
        String name = request.getParameter("name");

        List<Station> stations = stationMapper.getOtherStations(name);

        ReturnResult returnResult = new ReturnResult();
        returnResult.setCode(0);
        returnResult.setDisplay(0);
        returnResult.setMessage("");
        returnResult.setData(stations);

        return ReturnResultUtil.ReturnResultToJSON(returnResult);
    }

    /**
     * ɾ�����ݿ��������ݣ��Ա����µ���
     *
     * ��ֹ��Զ�̷�����ʹ�ô˷���������ɾ޴���ʧ
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAll", produces = "application/json;charset=utf-8", method = RequestMethod.POST)
    public String deleteAll() {

        stationMapper.deleteAll1();
        stationMapper.deleteAll2();
        stationMapper.deleteAll3();
        stationMapper.deleteAll4();
        stationMapper.deleteAll5();

        return "���ݿ���ճɹ�";
    }
}
