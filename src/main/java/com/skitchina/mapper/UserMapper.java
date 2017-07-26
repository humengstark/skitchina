package com.skitchina.mapper;


import com.skitchina.model.User;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/15.
 */
public interface UserMapper {

    //���������û�
    @Select("SELECT*FROM user")
    List<User> getAllUsers();

    //�����ֻ��Ų����û�
    @Select("SELECT*FROM user WHERE cellphone=#{cellphone}")
    User getUserByCellphone(String cellphone);

    //����û�
    @Insert("INSERT INTO user (cellphone,password,company_name,company_address,company_tel,name) VALUES (#{cellphone},#{password},#{company_name},#{company_address},#{company_tel},#{name})")
    void addUser(Map params);

    //����
    @Update("UPDATE `user` SET achievement=achievement+#{money} WHERE id=#{user_id}")
    void submit(Map params);

    //����ID����USER
    @Select("SELECT*FROM user WHERE id=#{id}")
    User getUserById(int id);

    //修改registration_id
    @Update("UPDATE `user` SET registration_id=#{registration_id} WHERE id=#{id})")
    void updateRegistrationId(Map params);

    @Update("UPDATE `user` SET new_waybill=new_waybill+1 WHERE station=#{station}")
    void updateNewWaybillNum(String station);

    @Select("SELECT new_waybill FROM `user` WHERE id=#{user_id}")
    int getNewWaybillNum(int user_id);

    @Update("UPDATE `user` SET new_waybill=0 WHERE id=#{user_id}")
    void setNewWaybill0(int user_Id);

}
