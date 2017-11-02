package com.skitchina.service;

import com.skitchina.mapper.CompleteWaybillMapper;
import com.skitchina.model.CompleteWaybill;
import com.skitchina.model.Waybill;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Tony Stark on 2017/9/15.
 */
@Service("completeWaybillMapperImpl")
public class CompleteWaybillMapperImpl implements CompleteWaybillMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void addCompleteWaybill(int waybill_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.CompleteWaybillMapper.addCompleteWaybill", waybill_id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<CompleteWaybill> getAllCompleteWaybills(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<CompleteWaybill> completeWaybillList = sqlSession.selectList("com.skitchina.mapper.CompleteWaybillMapper.getAllCompleteWaybills",params);
        sqlSession.commit();
        sqlSession.close();
        return completeWaybillList;
    }

    @Override
    public int getCompleteWaybillsNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.CompleteWaybillMapper.getCompleteWaybillsNum");
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    @Override
    public void deleteCompleteWaybillByWaybillId(int waybill_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.CompleteWaybillMapper.deleteCompleteWaybillByWaybillId", waybill_id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<Waybill> getCompleteWaybills(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Waybill> waybillList = sqlSession.selectList("com.skitchina.mapper.CompleteWaybillMapper.getCompleteWaybills", params);
        sqlSession.commit();
        sqlSession.close();
        return waybillList;
    }
}
