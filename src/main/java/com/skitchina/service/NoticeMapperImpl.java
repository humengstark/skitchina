package com.skitchina.service;

import com.skitchina.mapper.NoticeMapper;
import com.skitchina.model.Notice;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/27.
 */
@Service("noticeMapperImpl")
public class NoticeMapperImpl implements NoticeMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public Notice getNewNotice() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Notice notice = sqlSession.selectOne("com.skitchina.mapper.NoticeMapper.getNewNotice");
        sqlSession.commit();
        sqlSession.close();
        return notice;
    }

    public void addNotice(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.NoticeMapper.addNotice", params);
        sqlSession.commit();
        sqlSession.close();
    }

    public List<Notice> getAllNotices(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Notice> notices = sqlSession.selectList("com.skitchina.mapper.NoticeMapper.getAllNotices", params);
        sqlSession.commit();
        sqlSession.close();
        return notices;
    }

    public int getNoticesNum() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int num = sqlSession.selectOne("com.skitchina.mapper.NoticeMapper.getNoticesNum");
        sqlSession.commit();
        sqlSession.close();
        return num;
    }

    public void deleteNotice(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.NoticeMapper.deleteNotice", id);
        sqlSession.commit();
        sqlSession.close();
    }
}
