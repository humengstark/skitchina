package com.skitchina.service;

import com.skitchina.mapper.ClientWaybillMapper;
import com.skitchina.model.ClientWaybill;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hu meng on 2017/6/20.
 */
@Service("clientWaybillMapperImpl")
public class ClientWaybillMapperImpl implements ClientWaybillMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<ClientWaybill> getClientWaybill() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<ClientWaybill> clientWaybills = sqlSession.selectList("com.skitchina.mapper.ClientWaybillMapper.getClientWaybill");
        sqlSession.commit();
        sqlSession.close();
        return clientWaybills;
    }
}
