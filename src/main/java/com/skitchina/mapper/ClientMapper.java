package com.skitchina.mapper;

import com.skitchina.model.CheckSubmit;
import com.skitchina.model.Client;
import com.skitchina.model.Waybill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/13.
 */
public interface ClientMapper {

    @Insert("INSERT INTO client (cellphone,password,company_name,company_address,company_tel,name) " +
            "VALUES (#{cellphone},#{password},#{company_name},#{company_address},#{company_tel},#{name})")
    void addClient(Map params);

    //根据cellphone查找客户
    @Select("SELECT * FROM client WHERE cellphone=#{cellphone}")
    Client getClientByCellphone(String cellphone);

    //选择对账时间
    @Update("UPDATE client SET checktime=#{checkTime} WHERE id=#{id}")
    void updateCheckTime(Map params);

    //修改checkCondition
    @Update("UPDATE waybill SET check_condition=#{check_condition},check_id=#{check_id} WHERE waybill_id=#{waybill_id}")
    void updateCheckCondition(Map params);

    //根据client_id查询已对账的waybill
    @Select("SELECT*FROM waybill WHERE check_id=#{check_id} AND check_condition=1")
    List<Waybill> getScannedWaybillsByClientId(int id);

    //提交已扫描的订单
    @Update("UPDATE waybill SET check_condition=2 WHERE waybill_id=#{waybill_id}")
    void updateCheckCondition2(int waybill_id);

    //保存提交记录
    @Insert("INSERT INTO checksubmit (submit_time,waybill_ids,money,client_id) VALUES (#{submit_time},#{waybill_ids},#{money},#{client_id})")
    void addCheckSubmit(Map params);

    //根据ID搜索client
    @Select("SELECT*FROM client WHERE id=#{id}")
    Client getClientById(int id);

    //根据ID获取checksubmit
    @Select("SELECT*FROM checksubmit WHERE id=#{id}")
    CheckSubmit getCheckSubmitById(int id);

    //修改checksubmit的condition为1
    @Update("UPDATE checksubmit SET `condition`=1 WHERE id=#{id}")
    void updateCheckSubmitCondition(int id);

    //对账通过审核
    @Update("UPDATE waybill SET check_condition=3 WHERE id=#{id}")
    void updateCheckCondition3(int id);

    //修改帐户余额
    @Update("UPDATE client SET balance=balance+#{money} WHERE id=#{id}")
    void updateBalance(Map params);

    //显示帐户余额
    @Select("SELECT balance FROM client WHERE id=#{id}")
    double getClientBalance(int id);

    //修改提现密码
    @Update("UPDATE client SET draw_password=#{draw_password} WHERE id=#{id}")
    void updateDrawPassword(Map params);
}
