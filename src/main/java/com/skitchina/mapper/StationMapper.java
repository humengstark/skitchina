package com.skitchina.mapper;

import com.skitchina.model.Station;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by hu meng on 2017/4/21.
 */
public interface StationMapper {

    //查找所有网点
    @Select("SELECT*FROM station")
    List<Station> getAllStations();

    //根据name查找网点
    @Select("SELECT*FROM station WHERE `name`=#{name}")
    Station getStationByName(String name);

    //根据id查询网点
    @Select("SELECT*FROM station WHERE id=#{id}")
    Station getStationById(int id);

    //根据name查询其他所有网点
    @Select("SELECT*FROM station WHERE name<>#{name}")
    List<Station> getOtherStations(String name);

    //删除数据库所有
    @Delete("DELETE FROM contact")
    void deleteAll1();

    @Delete("DELETE FROM station")
    void deleteAll2();

    @Delete("DELETE FROM submit")
    void deleteAll3();

    @Delete("DELETE FROM user")
    void deleteAll4();

    @Delete("DELETE FROM waybill")
    void deleteAll5();
}
