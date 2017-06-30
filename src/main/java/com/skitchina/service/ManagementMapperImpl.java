package com.skitchina.service;

import com.skitchina.mapper.ManagementMapper;
import com.skitchina.model.*;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/5/2.
 */
@Service("managementMapperImpl")
public class ManagementMapperImpl implements ManagementMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Waybill> getAllWaybills(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getAllWaybills", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<User> getAllUsers() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getAllUsers");
        sqlSession.commit();
        sqlSession.close();
        return users;
    }

    public int getWaybillNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int pages = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillNum");
        sqlSession.commit();
        sqlSession.close();
        return pages;
    }

    public void deleteWaybillById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.ManagementMapper.deleteWaybillById", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateCondition(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updateCondition", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<User> getUsers(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getUsers", params);
        sqlSession.commit();
        sqlSession.close();
        return users;
    }

    public int getUserNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int userNum = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getUserNum");
        sqlSession.commit();
        sqlSession.close();
        return userNum;
    }

    public void updatePower(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updatePower", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void deleteUserById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.ManagementMapper.deleteUserById", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public Waybill getWaybillByWaybillId(int waybill_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Waybill waybill = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillByWaybillId", waybill_id);
        sqlSession.commit();
        sqlSession.close();
        return waybill;
    }

    public User getUserById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getUserById", id);
        sqlSession.commit();
        sqlSession.close();
        return user;
    }

    public void updateStation(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updateStation", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Station> getAllStations() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Station> stations = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getAllStations");
        sqlSession.commit();
        sqlSession.close();
        return stations;
    }

    public User getUserByCellphone(String cellphone) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getUserByCellphone", cellphone);
        sqlSession.commit();
        sqlSession.close();
        return user;
    }

    public void deleteStationById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.ManagementMapper.deleteStationById", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public int addStation(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int id = sqlSession.insert("com.skitchina.mapper.ManagementMapper.addStation", params);
        sqlSession.commit();
        sqlSession.close();
        return id;
    }

    public int getMaxStationId() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int id = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getMaxStationId");
        sqlSession.commit();
        sqlSession.close();
        return id;
    }

    public void updateArriveById(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updateArriveById", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateWaybillByWaybillId(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updateWaybillByWaybillId", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void invalidWaybillById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.invalidWaybillById", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateUserById(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updateUserById", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Waybill> getWaybillsByConsignorCompany(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getWaybillsByConsignorCompany", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public int getWaybillsByConsignorCompanyNum(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num=sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillsByConsignorCompanyNum",params);
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    public List<Waybill> getWaybillsByConsigneeCompany(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getWaybillsByConsigneeCompany", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public int getWaybillsByConsigneeCompanyNum(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillsByConsigneeCompanyNum", params);
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    public List<Waybill> getWaybillsByConsignorAndConsignee(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getWaybillsByConsignorAndConsignee", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public int getWaybillsByConsignorAndConsigneeNum(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillsByConsignorAndConsigneeNum", params);
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    public void updatePayway(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updatePayway", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void endWaybill(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.endWaybill", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public Waybill getWaybillById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Waybill waybill = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillById", id);
        sqlSession.commit();
        sqlSession.close();
        return waybill;
    }

    public void updateFreightOrPrice(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ManagementMapper.updateFreightOrPrice", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Waybill> getWaybillsNotSubmit(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getWaybillsNotSubmit", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public int getWaybillNotSubmitNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int waybillNotSubmitNum = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillNotSubmitNum");
        sqlSession.commit();
        sqlSession.close();
        return waybillNotSubmitNum;
    }

    public List<Submit> getAllSubmits(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Submit> submits = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getAllSubmits",params);
        sqlSession.commit();
        sqlSession.close();
        return submits;
    }

    public int getSubmitsNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int submitsNum = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getSubmitsNum");
        sqlSession.commit();
        sqlSession.close();
        return submitsNum;
    }

    public List<Waybill> getWaybillsReceiveAndNotSubmit(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getWaybillsReceiveAndNotSubmit", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public int getWaybillsReceiveAndNotSubmitNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getWaybillsReceiveAndNotSubmitNum");
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    public Submit getSubmitById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Submit submit = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getSubmitById", id);
        sqlSession.commit();
        sqlSession.close();
        return submit;
    }

    public List<CheckSubmit> getCheckSubmits(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<CheckSubmit> checkSubmits = sqlSession.selectList("com.skitchina.mapper.ManagementMapper.getCheckSubmits",params);
        sqlSession.commit();
        sqlSession.close();
        return checkSubmits;
    }

    public int getCheckSubmitsNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.ManagementMapper.getCheckSubmitsNum");
        sqlSession.commit();
        sqlSession.close();
        return num;
    }
}
