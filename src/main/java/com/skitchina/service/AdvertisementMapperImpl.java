package com.skitchina.service;

import com.skitchina.mapper.AdvertisementMapper;
import com.skitchina.model.Advertisement;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by hu meng on 2017/6/28.
 */
@Service("advertiseMapperImpl")
public class AdvertisementMapperImpl implements AdvertisementMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void addAdvertisement(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.AdvertisementMapper.addAdvertisement", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public Advertisement getAdvertisementByClientId(int client_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Advertisement advertisement = sqlSession.selectOne("com.skitchina.mapper.AdvertisementMapper.getAdvertisementByClientId", client_id);
        sqlSession.commit();
        sqlSession.close();
        return advertisement;
    }
}
