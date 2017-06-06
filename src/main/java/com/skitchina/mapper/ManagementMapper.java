package com.skitchina.mapper;

import com.skitchina.model.Station;
import com.skitchina.model.Submit;
import com.skitchina.model.User;
import com.skitchina.model.Waybill;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/5/2.
 * 后台管理页面mapper
 */
public interface ManagementMapper {

    //获取所有运单
    @Select("SELECT*FROM waybill WHERE waybill_id<>320720000 ORDER BY invalid,`condition`,time DESC,time1 DESC,time2 DESC,time3 DESC,time4 DESC,time5 DESC LIMIT #{m},#{rows}")
    List<Waybill> getAllWaybills(Map params);

    //获取所有用户
    @Select("SELECT*FROM user")
    List<User> getAllUsers();

    //获取订单数量
    @Select("SELECT COUNT(id) FROM waybill")
    int getWaybillNum();

    //根据id删除订单
    @Delete("DELETE FROM waybill WHERE id=#{id}")
    void deleteWaybillById(int id);

    //根据id修改condition
    @Update("UPDATE waybill SET `condition`=#{condition} WHERE id=#{id}")
    void updateCondition(Map params);

    //获取所有用户
    @Select("SELECT*FROM user ORDER BY power DESC,id LIMIT #{m},#{rows}")
    List<User> getUsers(Map params);

    //获取用户数量
    @Select("SELECT COUNT(id) FROM user")
    int getUserNum();

    //修改权限
    @Update("UPDATE user SET power=#{power} WHERE id=#{id}")
    void updatePower(Map params);

    //根据id删除用户
    @Delete("DELETE FROM user WHERE id=#{id}")
    void deleteUserById(int id);

    //根据waybill_id查询订单
    @Select("SELECT*FROM waybill WHERE waybill_id=#{waybill_id}")
    Waybill getWaybillByWaybillId(int waybill_id);

    //根据id查询用户
    @Select("SELECT*FROM user WHERE id=#{id}")
    User getUserById(int id);

    //修改网点
    @Update("UPDATE user SET station=#{station} WHERE id=#{id}")
    void updateStation(Map params);

    //查询所有网点
    @Select("SELECT*FROM station")
    List<Station> getAllStations();

    //根据cellphone查询用户
    @Select("SELECT*FROM user WHERE cellphone=#{cellphone}")
    User getUserByCellphone(String cellphone);

    //根据id删除网点
    @Delete("DELETE FROM station WHERE id=#{id}")
    void deleteStationById(int id);

    //增加网点
    @Insert("INSERT INTO station (name,arrive) VALUES (#{name},#{arrive})")
    int addStation(Map params);

    //获取网点的最大id
    @Select("SELECT MAX(id) FROM station")
    int getMaxStationId();

    //根据id修改到达
    @Update("UPDATE station SET arrive=#{arrive} WHERE id=#{id}")
    void updateArriveById(Map params);

    //根据waybill_id修改订单
    @Update("UPDATE waybill SET consignor_company=#{consignor_company},consignor_tel=#{consignor_tel}," +
            "origin=#{origin},consignor_address=#{consignor_address},consignee_company=#{consignee_company}," +
            "consignee_tel=#{consignee_tel},destination=#{destination},consignee_address=#{consignee_address}," +
            "price=#{price},freight=#{freight},number=#{number},remark=#{remark} WHERE waybill_id=#{waybill_id}")
    void updateWaybillByWaybillId(Map params);

    //作废订单
    @Update("UPDATE waybill SET invalid=1 WHERE id=#{id}")
    void invalidWaybillById(int id);

    //根据id修改用户
    @Update("UPDATE user SET name=#{name},cellphone=#{cellphone},password=#{password}," +
            "company_name=#{company_name},company_address=#{company_address}," +
            "company_tel=#{company_tel},achievement=#{achievement} WHERE id=#{id}")
    void updateUserById(Map params);

    //根据consignor_company查询订单
    @Select("SELECT*FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%') ORDER BY `condition`,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsignorCompany(Map params);

    @Select("SELECT COUNT(*) FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%')")
    int getWaybillsByConsignorCompanyNum(Map params);

    //根据consignee_company查询订单
    @Select("SELECT*FROM waybill WHERE consignee_company LIKE CONCAT('%','${consignee_company}','%') ORDER BY `condition`,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsigneeCompany(Map params);

    @Select("SELECT COUNT(*) FROM waybill WHERE consignee_company LIKE CONCAT('%','${consignee_company}','%')")
    int getWaybillsByConsigneeCompanyNum(Map params);

    //根据以上2个同时查询
    @Select("SELECT*FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%') AND consignee_company LIKE CONCAT('%','${consignee_company}','%') ORDER BY `condition`,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsignorAndConsignee(Map params);

    @Select("SELECT COUNT(*) FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%') AND consignee_company LIKE CONCAT('%','${consignee_company}','%')")
    int getWaybillsByConsignorAndConsigneeNum(Map params);

    //修改支付方式
    @Select("UPDATE waybill SET payway=#{payway},consignor_mark=1,consignee_mark=1 WHERE waybill_id=#{waybill_id}")
    void updatePayway(Map params);

    //结束订单
    @Update("UPDATE waybill SET `condition`=6 WHERE id=#{id}")
    void endWaybill(int id);

    //根据id查询订单
    @Select("SELECT*FROM waybill WHERE id=#{id}")
    Waybill getWaybillById(int id);

    //修改运费或者代收款
    @Update("UPDATE waybill SET freight=#{freight},price=#{price} WHERE id=#{id}")
    void updateFreightOrPrice(Map params);

    //获取还没有交账的订单
    @Select("SELECT*FROM waybill WHERE `condition`<>4 AND `condition`<>5 AND `condition`<>6 AND invalid=0 ORDER BY `condition` DESC,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsNotSubmit(Map params);

    @Select("SELECT COUNT(*) FROM waybill WHERE `condition`<>4 AND `condition`<>5 AND `condition`<>6 AND invalid=0")
    int getWaybillNotSubmitNum();

    //查询所有总账
    @Select("SELECT*FROM submit ORDER BY time4 DESC LIMIT #{m},#{rows}")
    List<Submit> getAllSubmits(Map params);

    @Select("SELECT COUNT(*) FROM submit")
    int getSubmitsNum();

    //查询已收账但是还未交战的订单
    @Select("SELECT*FROM waybill WHERE (`condition`=3 AND payway=0 AND invalid<>1) OR (payway=1 AND consignor_mark=0 AND invalid=0) OR (payway=1 AND consignee_mark=0 AND invalid=0) ORDER BY time LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsReceiveAndNotSubmit(Map params);

    @Select("SELECT COUNT(*) FROM waybill WHERE (`condition`=3 AND payway=0 AND invalid<>1) OR (payway=1 AND consignor_mark=0 AND invalid=0) OR (payway=1 AND consignee_mark=0 AND invalid=0)")
    int getWaybillsReceiveAndNotSubmitNum();

    //根据id查询submit
    @Select("SELECT*FROM submit WHERE id=#{id}")
    Submit getSubmitById(int id);
}
