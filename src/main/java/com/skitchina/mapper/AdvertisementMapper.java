package com.skitchina.mapper;

import com.skitchina.model.Advertisement;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/27.
 */
public interface AdvertisementMapper {

    //新增广告信息
    @Insert("INSERT INTO advertisement (client_id,simpleIntroduce,introduce,imgNum) VALUES (#{client_id},#{simpleIntroduce},#{introduce},#{imgNum})")
    void addAdvertisement(Map params);

    @Select("SELECT * FROM advertisement WHERE client_id=#{client_id}")
    Advertisement getAdvertisementByClientId(int client_id);

    @Delete("DELETE FROM advertisement WHERE client_id=#{client_id}")
    void deleteAdvertisementByClientId(int client_id);

    @Select("SELECT * FROM advertisement WHERE `condition`=1 ORDER BY show_time DESC LIMIT 0,3")
    List<Advertisement> getSimpleIntroduce2();

    //修改condition和show_time
    @Update("UPDATE advertisement SET `condition`=1,show_time=#{show_time} WHERE client_id=#{client_id}")
    void updateCondition(Map params);

    @Select("SELECT * FROM advertisement")
    List<Advertisement> getAllAdvertisements();

    //取消展示
    @Update("UPDATE advertisement SET `condition`=0 WHERE client_id=#{client_id}")
    void updateCondition0(int client_id);
}
