package com.skitchina.mapper;

import com.skitchina.model.Station;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by hu meng on 2017/4/21.
 */
public interface StationMapper {

    //������������
    @Select("SELECT*FROM station")
    List<Station> getAllStations();

    //����name��������
    @Select("SELECT*FROM station WHERE `name`=#{name}")
    Station getStationByName(String name);

    //����id��ѯ����
    @Select("SELECT*FROM station WHERE id=#{id}")
    Station getStationById(int id);

    //����name��ѯ������������
    @Select("SELECT*FROM station WHERE name<>#{name}")
    List<Station> getOtherStations(String name);

    //ɾ�����ݿ�����
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
