package com.skitchina.service;

import com.skitchina.mapper.ContactMapper;
import com.skitchina.model.Contact;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/18.
 */
@Service("contactMapperImpl")
public class ContactMapperImpl implements ContactMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public void addContact(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.ContactMapper.addContact", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Contact> getContactsByCellphone(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Contact> contacts=sqlSession.selectList("com.skitchina.mapper.ContactMapper.getContactsByCellphone", params);
        sqlSession.commit();
        sqlSession.close();
        return contacts;
    }

    public List<Contact> getContactsByCompany(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Contact> contacts=sqlSession.selectList("com.skitchina.mapper.ContactMapper.getContactsByCompany", params);
        sqlSession.commit();
        sqlSession.close();
        return contacts;
    }

    public Contact getContactByCellphone(String cellphone) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Contact contact = sqlSession.selectOne("com.skitchina.mapper.ContactMapper.getContactByCellphone", cellphone);
        sqlSession.commit();
        sqlSession.close();
        return contact;
    }

    public void deleteContact(String cellphone) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.ContactMapper.deleteContact", cellphone);
        sqlSession.commit();
        sqlSession.close();
    }

    public void deleteContactById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.ContactMapper.deleteContactById", id);
        sqlSession.commit();
        sqlSession.close();
    }
}
