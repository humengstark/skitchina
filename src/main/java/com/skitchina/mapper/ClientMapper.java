package com.skitchina.mapper;

import com.skitchina.model.Client;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * Created by hu meng on 2017/6/13.
 */
public interface ClientMapper {

    @Insert("INSERT INTO client (cellphone,password,company_name,company_address,company_tel,name) " +
            "VALUES (#{cellphone},#{password},#{company_name},#{company_address},#{company_tel},#{name})")
    void addClient(Map params);

    //根据cellphone查找客户
    @Select("SELECT * FROM client WHERE cellphone=#{cellphone}")
    Client getClientByCellphone(String cellphone);

    //选择对账时间
    @Update("UPDATE client SET checktime=#{checkTime} WHERE id=#{id}")
    void updateCheckTime(Map params);
}
