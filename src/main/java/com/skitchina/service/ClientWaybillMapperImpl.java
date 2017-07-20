package com.skitchina.service;

import com.skitchina.mapper.ClientWaybillMapper;
import com.skitchina.model.ClientWaybill;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/20.
 */
@Service("clientWaybillMapperImpl")
public class ClientWaybillMapperImpl implements ClientWaybillMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void addClientWaybill(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.ClientWaybillMapper.addClientWaybill", params);
        sqlSession.commit();
        sqlSession.close();
    }


    public List<ClientWaybill> getClientWaybills(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<ClientWaybill> clientWaybills = sqlSession.selectList("com.skitchina.mapper.ClientMapper.getClientWaybills", params);
        sqlSession.commit();
        sqlSession.close();
        return clientWaybills;
    }

    public void updateClientWaybillCondition(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateClientWaybillCondition", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateClientWaybillCondition2(int waybill_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.ClientMapper.updateClientWaybillCondition2", waybill_id);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<ClientWaybill> getReceivebleClientWaybills(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<ClientWaybill> clientWaybills = sqlSession.selectList("com.skitchina.mapper.ClientMapper.getReceivebleClientWaybills", params);
        sqlSession.commit();
        sqlSession.close();
        return clientWaybills;
    }
}
