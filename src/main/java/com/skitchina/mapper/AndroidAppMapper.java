package com.skitchina.mapper;

import com.skitchina.model.AndroidApp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * Created by hu meng on 2017/6/6.
 */
public interface AndroidAppMapper {

    @Insert("INSERT INTO androidapp (id,version,time,name) VALUES (#{id},#{version},#{time},#{name})")
    void addAndroidApp(Map params);

    @Update("UPDATE androidapp SET url=#{url} WHERE id=#{id}")
    void updateAppUrl(Map params);

    @Select("SELECT*FROM androidapp WHERE id=(SELECT MAX(id) FROM androidapp)")
    AndroidApp getLastAndroidApp();

    @Delete("DELETE FROM androidapp WHERE id=#{id}")
    void deleteAndroidApp(int id);

    @Select("SELECT*FROM androidapp WHERE id=#{id}")
    AndroidApp getAndroidAppById(int id);

    //增加假数据用
    @Insert("INSERT INTO tb_users (passport,password,companyname,activated_at,created_at) VALUES (#{passport},#{password},#{companyname},#{activated_at},#{created_at})")
    void addUser(Map params);
}
