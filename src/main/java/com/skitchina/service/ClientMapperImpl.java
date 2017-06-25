package com.skitchina.service;

import com.skitchina.mapper.ClientMapper;
import com.skitchina.model.Client;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
