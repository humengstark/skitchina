package com.skitchina.mapper;

import com.skitchina.model.CompleteWaybill;
import com.skitchina.model.Waybill;
import org.apache.ibatis.annotations.Delete;
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

    @Select("SELECT COUNT(*) FROM waybill,completewaybill WHERE waybill.waybill_id=completewaybill.waybill_id AND waybill.`condition`<>6")
    int getCompleteWaybillsNum();

    @Delete("DELETE FROM completewaybill WHERE waybill_id=#{waybill_id}")
    void deleteCompleteWaybillByWaybillId(int waybill_id);

    @Select("SELECT*FROM waybill,completewaybill WHERE waybill.waybill_id=completewaybill.waybill_id AND waybill.`condition`<>6 LIMIT #{m},#{rows}")
    List<Waybill> getCompleteWaybills(Map params);

}
