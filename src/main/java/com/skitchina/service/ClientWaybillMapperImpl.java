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
}
