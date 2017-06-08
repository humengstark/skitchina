package com.skitchina.mapper;

import com.skitchina.model.AndroidApp;
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
    void updateAppUrl(int id);

    @Select("SELECT*FROM androidapp WHERE id=(SELECT MAX(id) FROM androidapp)")
    AndroidApp getLastAndroidApp();
}
