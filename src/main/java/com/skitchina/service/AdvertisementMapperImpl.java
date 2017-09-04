package com.skitchina.service;

import com.skitchina.mapper.AdvertisementMapper;
import com.skitchina.model.Advertisement;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void deleteAdvertisementByClientId(int client_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.AdvertisementMapper.deleteAdvertisementByClientId", client_id);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Advertisement> getSimpleIntroduce2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Advertisement> advertisements = sqlSession.selectList("com.skitchina.mapper.AdvertisementMapper.getSimpleIntroduce2");
        sqlSession.commit();
        sqlSession.close();
        return advertisements;
    }

    public void updateCondition(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.AdvertisementMapper.updateCondition", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Advertisement> getAllAdvertisements() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Advertisement> advertisementList = sqlSession.selectList("com.skitchina.mapper.AdvertisementMapper.getAllAdvertisements");
        sqlSession.commit();
        sqlSession.close();
        return advertisementList;
    }

    public void updateCondition0(int client_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.Advertisement.updateCondition0", client_id);
        sqlSession.commit();
        sqlSession.close();
    }
}
