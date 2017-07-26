package com.skitchina.service;

import com.skitchina.mapper.UserMapper;
import com.skitchina.model.Achivement;
import com.skitchina.model.User;
import com.skitchina.model.Waybill;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/15.
 */
@Service("userMapperImpl")
public class UserMapperImpl implements UserMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<User> getAllUsers() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("com.skitchina.mapper.UserMapper.getAllUsers");
        sqlSession.commit();
        sqlSession.close();
        return users;
    }

    public User getUserByCellphone(String cellphone) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = sqlSession.selectOne("com.skitchina.mapper.UserMapper.getUserByCellphone", cellphone);
        sqlSession.commit();
        sqlSession.close();
        return user;
    }

    public void addUser(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.UserMapper.addUser", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void submit(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.UserMapper.submit", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public User getUserById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user=sqlSession.selectOne("com.skitchina.mapper.UserMapper.getUserById", id);
        sqlSession.commit();
        sqlSession.close();
        return user;
    }

    public void updateRegistrationId(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.UserMapper.updateRegistrationId", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public void updateNewWaybillNum(String station) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.UserMapper.updateNewWaybillNum", station);
        sqlSession.commit();
        sqlSession.close();
    }

    public int getNewWaybillNum(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.UserMapper.getNewWaybillNum", user_id);
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    public void setNewWaybill0(int user_id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.update("com.skitchina.mapper.UserMapper.setNewWaybill0", user_id);
        sqlSession.commit();
        sqlSession.close();
    }

}
