package com.skitchina.service;


import com.skitchina.mapper.SubmitMapper;
import com.skitchina.model.Submit;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/25.
 */
@Service("submitMapperImpl")
public class SubmitMapperImpl implements SubmitMapper{

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Submit> getAllSubmits(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Submit> submits= sqlSession.selectList("com.skitchina.mapper.SubmitMapper.getAllSubmits",params);
        sqlSession.commit();
        sqlSession.close();
        return submits;
    }

    public void addSubmit(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.SubmitMapper.addSubmit", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public Submit getSubmitById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Submit submit= sqlSession.selectOne("com.skitchina.mapper.SubmitMapper.getSubmitById", id);
        sqlSession.commit();
        sqlSession.close();
        return submit;
    }
}
