package com.skitchina.mapper;

import com.skitchina.model.Question;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Tony Stark on 2017/9/10.
 */
public interface QuestionMapper {

    @Insert("INSERT INTO question (title,content,question_time) VALUES (#{title},#{content},#{question_time})")
    void addQuestion(Map params);

    @Select("SELECT*FROM question WHERE id=#{id}")
    Question getQuestionById(int id);

    @Select("SELECT*FROM question")
    List<Question> getAllQuestions();

    @Delete("DELETE FROM question WHERE id=#{id}")
    void deleteQuestion(int id);
}
