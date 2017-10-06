package com.skitchina.service;

import com.skitchina.mapper.WaybillMapper;
import com.skitchina.model.Waybill;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/18.
 */
@Service("waybillMapperImpl")
public class WaybillMapperImpl implements WaybillMapper {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void addWaybill(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.WaybillMapper.addWaybill", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public Waybill getMaxIdWaybill() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Waybill waybill= sqlSession.selectOne("com.skitchina.mapper.WaybillMapper.getMaxIdWaybill");
        sqlSession.commit();
        sqlSession.close();
        return waybill;
    }

    public List<Waybill> getWaybillsByOrigin(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByOrigin", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getWaybillsByDestination(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByDestination", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getWaybillsByUserId(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByUserId", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public void updateCondition1(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateCondition1", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateCondition2(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateCondition2", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateCondition4(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateCondition4", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateCondition5(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateCondition5", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public Waybill getWaybillByWaybillId(int waybill_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Waybill waybill= sqlSession.selectOne("com.skitchina.mapper.WaybillMapper.getWaybillByWaybillId", waybill_id);
        sqlSession.commit();
        sqlSession.close();
        return waybill;
    }

    public void receive(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.receive", params);
        sqlSession.commit();
        sqlSession.close();
    }

//    public List<Waybill> getReceivableWaybillsAndNotSubmit(int user2_id) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getReceivableWaybillsAndNotSubmit", user2_id);
//        sqlSession.commit();
//        sqlSession.close();
//        return waybills;
//    }

//    public List<Waybill> getSubmitWaybillsAndNotComplete(int user2_id) {
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getSubmitWaybillsAndNotComplete", user2_id);
//        sqlSession.commit();
//        sqlSession.close();
//        return waybills;
//    }

    public Waybill getWaybillById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Waybill waybill = sqlSession.selectOne("com.skitchina.mapper.WaybillMapper.getWaybillById", id);
        sqlSession.commit();
        sqlSession.close();
        return waybill;
    }

    public void invalidWaybill(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.invalidWaybill", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Waybill> getWaybillsByUserIdAndTime4(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill>waybills= sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByUserIdAndTime4", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getWaybillsByUserIdAndConsignorMark(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByUserIdAndConsignorMark", user_id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public void updateConsignorMark(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateConsignorMark", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateConsigneeMark(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateConsigneeMark", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public Waybill getWaybillByWaybill_id(int waybill_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Waybill waybill = sqlSession.selectOne("com.skitchina.mapper.WaybillMapper.getWaybillByWaybill_id", waybill_id);
        sqlSession.commit();
        sqlSession.close();
        return waybill;
    }

    public List<Waybill> getWaybillsByConsignorCompany(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByConsignorCompany", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getWaybillsByConsigneeCompany(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByConsigneeCompany", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getWaybillsByConsignor0(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByConsignor0", user_id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getWaybillsByConsignee0(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByConsignee0", user_id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public void updateConsignorMark2(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateConsignorMark2", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateConsigneeMark2(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateConsigneeMark2", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateWaybillUser2Id(Map parmas) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateWaybillUser2Id", parmas);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateUser3Id(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateUser3Id", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Waybill> getWaybillsByUser3Id(int user3_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsByUser3Id", user3_id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getFreightReceivableWaybillsByStation(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getFreightReceivableWaybillsByStation", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getPriceReceivableWaybills(int user2_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getPriceReceivableWaybills", user2_id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getFreightSubmitWaybills(int user3_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getFreightSubmitWaybills", user3_id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getPriceSubmitWaybills(int user2_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getPriceSubmitWaybills", user2_id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getReceivableWaybillsAndNotSubmit(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getReceivableWaybillsAndNotSubmit", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public List<Waybill> getSubmitWaybillsAndNotComplete(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getSubmitWaybillsAndNotComplete", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public void updateConsignorMark3(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateConsignorMark3", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateConsigneeMark3(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateConsigneeMark3", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateUser2Time(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateUser2Time", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateUser3Time(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateUser3Time", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Waybill> getWaybillsNotSubmit(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getWaybillsNotSubmit", params);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public void updateCondition3(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateCondition3", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateUser1Id(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.updateUser1Id", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public Waybill getLastWaybillByUserId(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Waybill waybill=sqlSession.selectOne("com.skitchina.mapper.WaybillMapper.getLastWaybillByUserId", user_id);
        sqlSession.commit();
        sqlSession.close();
        return waybill;
    }


    public void invalidWaybill2(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.invalidWaybill2", id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<Waybill> getInvalid2Waybills() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.WaybillMapper.getInvalid2Waybills");
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    @Override
    public void notInvalidWaybill(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.WaybillMapper.notInvalidWaybill", id);
        sqlSession.commit();
        sqlSession.close();
    }
}
