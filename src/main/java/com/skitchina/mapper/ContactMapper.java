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
 * ���²�ѯȫΪģ����ѯ
 */
public interface ContactMapper {

    //��ӳ�����ϵ��
    @Insert("INSERT INTO contact (user_id,cellphone,company,address) VALUES (#{user_id},#{cellphone},#{company},#{address})")
    void addContact(Map params);

    //����cellphone��ѯcontact
    @Select("SELECT*FROM contact WHERE cellphone LIKE CONCAT('%','${value}','%') LIMIT #{m},#{rows}")
    List<Contact> getContactsByCellphone(Map params);

    //����company��ѯcontact
    @Select("SELECT*FROM contact WHERE company LIKE CONCAT('%','${value}','%') LIMIT #{m},#{rows}")
    List<Contact> getContactsByCompany(Map params);

    //����cellphone��ѯ contact
    @Select("SELECT*FROM contact WHERE cellphone=#{cellphone}")
    Contact getContactByCellphone(String cellphone);

    //����cellphoneɾ��contact
    @Delete("DELETE FROM contact WHERE cellphone=#{cellphone}")
    void deleteContact(String cellphone);

    //����idɾ��contact
    @Delete("DELETE FROM contact WHERE id=#{id}")
    void deleteContactById(int id);
}
