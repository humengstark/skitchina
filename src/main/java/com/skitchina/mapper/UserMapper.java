package com.skitchina.mapper;


import com.skitchina.model.User;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/15.
 */
public interface UserMapper {

    //查找所有用户
    @Select("SELECT*FROM user")
    List<User> getAllUsers();

    //根据手机号查找用户
    @Select("SELECT*FROM user WHERE cellphone=#{cellphone}")
    User getUserByCellphone(String cellphone);

    //添加用户
    @Insert("INSERT INTO user (cellphone,password,company_name,company_address,company_tel,name) VALUES (#{cellphone},#{password},#{company_name},#{company_address},#{company_tel},#{name})")
    void addUser(Map params);

    //交账
    @Update("UPDATE `user` SET achievement=achievement+#{money} WHERE id=#{user_id}")
    void submit(Map params);

    //根据ID查找USER
    @Select("SELECT*FROM user WHERE id=#{id}")
    User getUserById(int id);
}
