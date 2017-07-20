package com.skitchina.mapper;

import com.skitchina.model.ClientWaybill;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/20.
 */
public interface ClientWaybillMapper {

    void addClientWaybill(Map params);

    //获取可以接的单子
    @Select("SELECT*FROM clientwaybill WHERE origin=#{station} AND `condition`=0 ORDER BY time1 DESC LIMIT #{m},#{rows}")
    List<ClientWaybill> getClientWaybills(Map params);

    //修改clientWaybill的状态
    @Update("UPDATE clientwaybill SET `condition`=1,user_id=#{user_id},client_id=#{client_id},waybill_id=#{waybill_id} WHERE id=#{clientWaybill_id}")
    void updateClientWaybillCondition(Map params);

    //修改clientWaybill的状态为完成
    @Update("UPDATE clientwaybill SET `condition`=2 WHERE waybill_id=#{waybill_id}")
    void updateClientWaybillCondition2(int waybill_id);

    //根据user_id查询已经接了的单子
    @Select("SELECT*FROM clientwaybill WHERE user_id=#{user_id} AND `condition`=1 ORDER BY time1 DESC LIMIT #{m},#{rows}")
    List<ClientWaybill> getReceivebleClientWaybills(Map params);
}
