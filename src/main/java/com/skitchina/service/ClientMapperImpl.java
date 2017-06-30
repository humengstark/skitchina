package com.skitchina.service;

import com.skitchina.mapper.ClientMapper;
import com.skitchina.model.CheckSubmit;
import com.skitchina.model.Client;
import com.skitchina.model.Waybill;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/13.
 */
@Service("clientMapperImpl")
public class ClientMapperImpl implements ClientMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void addClient(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.ClientMapper.addClient", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public Client getClientByCellphone(String cellphone) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Client client = sqlSession.selectOne("com.skitchina.mapper.ClientMapper.getClientByCellphone", cellphone);
        sqlSession.commit();
        sqlSession.close();
        return client;
    }

    public void updateCheckTime(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateCheckTime", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateCheckCondition(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateCheckCondition", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Waybill> getScannedWaybillsByClientId(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybills = sqlSession.selectList("com.skitchina.mapper.ClientMapper.getScannedWaybillsByClientId", id);
        sqlSession.commit();
        sqlSession.close();
        return waybills;
    }

    public void updateCheckCondition2(int waybill_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateCheckCondition2", waybill_id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void addCheckSubmit(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.ClientMapper.addCheckSubmit", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public Client getClientById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Client client = sqlSession.selectOne("com.skitchina.mapper.ClientMapper.getClientById", id);
        sqlSession.commit();
        sqlSession.close();
        return client;
    }

    public CheckSubmit getCheckSubmitById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        CheckSubmit checkSubmit = sqlSession.selectOne("com.skitchina.mapper.ClientMapper.getCheckSubmitById", id);
        sqlSession.commit();
        sqlSession.close();
        return checkSubmit;
    }

    public void updateCheckSubmitCondition(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateCheckSubmitCondition", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateCheckCondition3(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateCheckCondition3", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateBalance(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateBalance", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public double getClientBalance(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        double balance = sqlSession.selectOne("com.skitchina.mapper.ClientMapper.getClientBalance", id);
        sqlSession.commit();
        sqlSession.close();
        return balance;
    }

    public void updateDrawPassword(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateDrawPassword", params);
        sqlSession.commit();
        sqlSession.close();
    }
}
