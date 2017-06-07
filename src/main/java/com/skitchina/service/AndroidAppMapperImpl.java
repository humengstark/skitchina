package com.skitchina.service;

import com.skitchina.mapper.AndroidAppMapper;
import com.skitchina.model.AndroidApp;
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

    public AndroidApp getLastAndroidApp() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AndroidApp androidApp = sqlSession.selectOne("com.skitchina.mapper.AndroidAppMapper.getLastAndroidApp");
        sqlSession.commit();
        sqlSession.close();
        return androidApp;
    }
}
