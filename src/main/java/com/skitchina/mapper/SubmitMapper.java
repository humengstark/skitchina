package com.skitchina.mapper;


import com.skitchina.model.Submit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/25.
 */
public interface SubmitMapper {

    //查找所有Submit
    @Select("SELECT*FROM submit ORDER BY time4 DESC LIMIT #{m},#{rows}")
    List<Submit> getAllSubmits(Map params);

    //add submit
    @Insert("INSERT INTO submit (user2_id,achievement,time4,name,freight,price,freightandprice) VALUES (#{user2_id},#{achievement},#{time4},#{name},#{freight},#{price},#{freightandprice})")
    void addSubmit(Map params);

    //根据user_id查询submit
    @Select("SELECT*FROM submit WHERE id=#{id}")
    Submit getSubmitById(int id);


}
