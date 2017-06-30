package com.skitchina.service;

import com.skitchina.mapper.DrawMoneyMapper;
import com.skitchina.model.DrawMoney;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/26.
 */
@Service("drawMoneyMapperImpl")
public class DrawMoneyMapperImpl implements DrawMoneyMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void addDrawMoney(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.DrawMoneyMapper.addDrawMoney", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<DrawMoney> getDrawMoneys(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<DrawMoney> drawMoneys = sqlSession.selectList("com.skitchina.mapper.DrawMoneyMapper.getDrawMoneys", params);
        sqlSession.commit();
        sqlSession.close();
        return drawMoneys;
    }

    public int getDrawMoneysNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.DrawMoneyMapper.getDrawMoneysNum");
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    public void updateDrawCondition(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.DrawMoneyMapper.updateDrawCondition", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public void errorDrawMoney(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.DrawMoneyMapper.errorDrawMoney", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public DrawMoney getDrawMoneyById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        DrawMoney drawMoney = sqlSession.selectOne("com.skitchina.mapper.DrawMoneyMapper.getDrawMoneyById", id);
        sqlSession.commit();
        sqlSession.close();
        return drawMoney;
    }
}
