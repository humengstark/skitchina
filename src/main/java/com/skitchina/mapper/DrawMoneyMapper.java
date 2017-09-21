package com.skitchina.mapper;

import com.skitchina.model.DrawMoney;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/26.
 */
public interface DrawMoneyMapper {

    @Insert("INSERT INTO drawmoney (client_id,bank,bankcard,realname,draw_time,money) VALUES (#{client_id},#{bank},#{bankcard},#{realname},#{draw_time},#{money})")
    void addDrawMoney(Map params);

    @Select("SELECT*FROM drawmoney ORDER BY draw_time DESC LIMIT #{m},#{rows}")
    List<DrawMoney> getDrawMoneys(Map params);

    @Select("SELECT COUNT(*) FROM drawmoney")
    int getDrawMoneysNum();

    @Update("UPDATE drawmoney SET draw_condition=1 WHERE id=#{id}")
    void updateDrawCondition(int id);

    @Update("UPDATE drawmoney SET draw_condition=2 WHERE id=#{id}")
    void errorDrawMoney(int id);

    @Select("SELECT*FROM drawmoney WHERE id=#{id}")
    DrawMoney getDrawMoneyById(int id);
}
