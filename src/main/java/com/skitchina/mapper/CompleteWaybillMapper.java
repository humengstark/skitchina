package com.skitchina.mapper;

import com.skitchina.model.CompleteWaybill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Tony Stark on 2017/9/15.
 */
public interface CompleteWaybillMapper {

    //添加
    @Insert("INSERT INTO completewaybill (waybill_id) VALUES (#{waybill_id})")
    void addCompleteWaybill(int waybill_id);

    @Select("SELECT * FROM completewaybill ORDER BY id DESC LIMIT #{m},#{rows}")
    List<CompleteWaybill> getAllCompleteWaybills(Map params);

    @Select("SELECT COUNT(*) FROM completewaybill")
    int getCompleteWaybillsNum();

}
