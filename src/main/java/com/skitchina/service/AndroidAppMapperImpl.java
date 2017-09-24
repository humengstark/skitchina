package com.skitchina.service;

import com.skitchina.mapper.AndroidAppMapper;
import com.skitchina.model.AndroidApp;
import com.sun.tools.corba.se.idl.constExpr.And;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hu meng on 2017/6/6.
 */
@Service("androidAppMapperImpl")
public class AndroidAppMapperImpl implements AndroidAppMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void addAndroidApp(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.AndroidAppMapper.addAndroidApp", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateAppUrl(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.AndroidAppMapper.updateAppUrl", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public AndroidApp getLastAndroidApp() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AndroidApp androidApp = sqlSession.selectOne("com.skitchina.mapper.AndroidAppMapper.getLastAndroidApp");
        sqlSession.commit();
        sqlSession.close();
        return androidApp;
    }

    public void deleteAndroidApp(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.AndroidAppMapper.deleteAndroidApp", id);
        sqlSession.commit();
        sqlSession.close();
    }

    public AndroidApp getAndroidAppById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AndroidApp androidApp = sqlSession.selectOne("com.skitchina.mapper.AndroidAppMapper.getAndroidAppById", id);
        sqlSession.commit();
        sqlSession.close();
        return androidApp;
    }

    @Override
    public void addUser(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.AndroidAppMapper.addUser", params);
        sqlSession.commit();
        sqlSession.close();
    }
}
