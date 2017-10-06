package com.skitchina.mapper;

/**
 * Created by hu meng on 2017/4/18.
 */

import com.skitchina.model.Contact;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import sun.misc.resources.Messages_pt_BR;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.List;
import java.util.Map;

/**
 * 以下查询全为模糊查询
 */
public interface ContactMapper {

    //添加常用联系人
    @Insert("INSERT INTO contact (user_id,cellphone,company,address) VALUES (#{user_id},#{cellphone},#{company},#{address})")
    void addContact(Map params);

    //根据cellphone查询contact
    @Select("SELECT*FROM contact WHERE cellphone LIKE CONCAT('%','${value}','%') LIMIT #{m},#{rows}")
    List<Contact> getContactsByCellphone(Map params);

    //根据company查询contact
    @Select("SELECT*FROM contact WHERE company LIKE CONCAT('%','${value}','%') LIMIT #{m},#{rows}")
    List<Contact> getContactsByCompany(Map params);

    //根据cellphone查询 contact
    @Select("SELECT*FROM contact WHERE cellphone=#{cellphone}")
    Contact getContactByCellphone(String cellphone);

    //根据cellphone删除contact
    @Delete("DELETE FROM contact WHERE cellphone=#{cellphone}")
    void deleteContact(String cellphone);

    //根据id删除contact
    @Delete("DELETE FROM contact WHERE id=#{id}")
    void deleteContactById(int id);
}
