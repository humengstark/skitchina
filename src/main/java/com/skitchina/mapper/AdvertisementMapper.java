package com.skitchina.mapper;

import com.skitchina.model.Advertisement;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by hu meng on 2017/6/27.
 */
public interface AdvertisementMapper {

    //新增广告信息
    @Insert("INSERT INTO advertisement (client_id,simpleIntroduce,introduce,imgNum) VALUES (#{client_id},#{simpleIntroduce},#{introduce},#{imgNum})")
    void addAdvertisement(Map params);

    @Select("SELECT*FROM advertisement WHERE client_id=#{client_id}")
    Advertisement getAdvertisementByClientId(int client_id);
}
