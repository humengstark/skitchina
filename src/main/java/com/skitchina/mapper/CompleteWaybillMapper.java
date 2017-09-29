package com.skitchina.mapper;

import com.skitchina.model.CompleteWaybill;
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

    @Select("SELECT * FROM completewaybill ORDER BY id DESC")
    List<CompleteWaybill> getAllCompleteWaybills();

    @Select("SELECT COUNT(*) FROM completewaybill")
    int getCompleteWaybillsNum();

    @Delete("DELETE FROM completewaybill WHERE waybill_id=#{waybill_id}")
    void deleteCompleteWaybillByWaybillId(int waybill_id);

}
