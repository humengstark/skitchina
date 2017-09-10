package com.skitchina.service;

import com.skitchina.mapper.QuestionMapper;
import com.skitchina.model.Question;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Tony Stark on 2017/9/10.
 */
@Service("questionMapperImpl")
public class QuestionMapperImpl implements QuestionMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void addQuestion(Map params) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.skitchina.mapper.QuestionMapper.addQuestion", params);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public Question getQuestionById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Question question = sqlSession.selectOne("com.skitchina.mapper.QuestionMapper.getQuestionById",id);
        sqlSession.commit();
        sqlSession.close();
        return question;
    }

    @Override
    public List<Question> getAllQuestions() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Question> questionList = sqlSession.selectList("com.skitchina.mapper.QuestionMapper.getAllQuestions");
        sqlSession.commit();
        sqlSession.close();
        return questionList;
    }

    @Override
    public void deleteQuestion(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.QuestionMapper.deleteQuestion",id);
        sqlSession.commit();
        sqlSession.close();
    }
}
