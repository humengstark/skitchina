package com.skitchina.mapper;

import com.skitchina.model.AndroidApp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by hu meng on 2017/6/6.
 */
public interface AndroidAppMapper {

    @Insert("INSERT INTO androidapp (version,address,time,text) VALUES (#{version},#{address},#{time},#{text})")
    void addAndroidApp(Map params);

    @Select("SELECT*FROM androidapp WHERE id=MAX(id)")
    AndroidApp getLastAndroidApp();
}
