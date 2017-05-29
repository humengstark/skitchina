package com.skitchina.mapper;

import com.skitchina.model.Waybill;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/4/18.
 */
public interface WaybillMapper {

    //�µ�
    @Insert("INSERT INTO waybill (user_id,waybill_id,origin,destination,consignor_tel,consignee_tel," +
            "consignor_company,consignee_company,consignor_address,consignee_address,price,freight," +
            "number,payway,`condition`,consignor_mark,consignee_mark,remark,time) VALUES (#{user_id},#{waybill_id}," +
            "#{origin},#{destination},#{consignor_tel},#{consignee_tel},#{consignor_company},#{consignee_company}," +
            "#{consignor_address},#{consignee_address},#{price},#{freight},#{number},#{payway},#{condition},#{consignor_mark}," +
            "#{consignee_mark},#{remark},#{time})")
    void addWaybill(Map params);

    //�ҳ�waybill_id�����˵�
    @Select("SELECT*FROM waybill WHERE waybill_id=(SELECT MAX(waybill_id) FROM waybill)")
    Waybill getMaxIdWaybill();

    //����origin,condition��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE origin=#{origin} AND `condition`=#{condition} AND invalid<>1 ORDER BY time DESC LIMIT #{m},#{rows}")
    @Results()
    List<Waybill> getWaybillsByOrigin(Map params);

    //����destination,condition��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE destination=#{destination} AND `condition`=#{condition} AND invalid<>1 ORDER BY time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByDestination(Map params);

    //����user_id��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE user_id=#{user_id} AND invalid<>1 ORDER BY time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByUserId(Map params);

    //�޸��˵�״̬Ϊ1
    @Update("UPDATE waybill SET `condition`=1,time1=#{time1} WHERE id=#{id}")
    void updateCondition1(Map params);

    //�޸��˵�״̬Ϊ2
    @Update("UPDATE waybill SET `condition`=2,time2=#{time2} WHERE id=#{id}")
    void updateCondition2(Map params);

    //�޸��˵�״̬Ϊ4
    @Update("UPDATE waybill SET `condition`=4,time4=#{time4} WHERE id=#{id}")
    void updateCondition4(Map params);

    //�޸��˵�״̬Ϊ5
    @Update("UPDATE waybill SET `condition`=5,time5=#{time5} WHERE id=#{id}")
    void updateCondition5(Map params);

    //����waybill_id��ѯû�����ϵ��˵�
    @Select("SELECT*FROM waybill WHERE waybill_id=#{waybill_id} AND invalid<>1 ")
    Waybill getWaybillByWaybillId(int waybill_id);

    //���ˣ��޸�״̬Ϊ3
    @Update("UPDATE waybill SET user2_id=#{user2_id},`condition`=3,time3=#{time3} WHERE id=#{id} ")
    void receive(Map params);

    //����user2_id��ѯ���տû�н��˵��˵�������
//    @Select("SELECT*FROM waybill WHERE user2_id=#{user2_id} AND `condition`=3 AND payway=0 AND invalid<>1")
//    List<Waybill> getReceivableWaybillsAndNotSubmit(int user2_id);

    //����user2_id��ѯ�ѽ��˵��ǻ�δ��ɵ��˵�
//    @Select("SELECT*FROM waybill WHERE user2_id=#{user2_id} AND `condition`=4 AND payway=0 AND invalid<>1")
//    List<Waybill> getSubmitWaybillsAndNotComplete(int user2_id);

    //����id��ѯWaybill
    @Select("SELECT*FROM waybill WHERE id=#{id}")
    Waybill getWaybillById(int id);

    //�����˵�
    @Update("UPDATE waybill SET invalid=1 WHERE id=#{id}")
    void invalidWaybill(int id);

    //����time4��user_id��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE user2_id=#{user2_id} AND time4=#{time4} AND invalid<>1 LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByUserIdAndTime4(Map params);

    //���ݷ�����ID ��ѯ �ָ��˵�
    @Select("SELECT*FROM waybill WHERE user_id=#{user_id} AND consignor_mark=1 AND invalid<>1")
    List<Waybill> getWaybillsByUserIdAndConsignorMark(int user_id);

    //�޸�consignor_markΪ0
    @Update("UPDATE waybill SET consignor_mark=0 WHERE id=#{id}")
    void updateConsignorMark(int id);

    //�޸�consignee_markΪ0
    @Update("UPDATE waybill SET consignee_mark=0 WHERE id=#{id}")
    void updateConsigneeMark(int id);
    //����waybill_id��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE waybill_id=#{waybill_id} AND invalid<>1")
    Waybill getWaybillByWaybill_id(int waybill_id);

    //����ConsignorCompany��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%') AND invalid<>1 ORDER BY time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsignorCompany(Map params);

    //����ConsigneeCompany��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE consignee_company LIKE CONCAT('%','${consignee_company}','%') AND invalid<>1 ORDER BY time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsigneeCompany(Map params);

    //����consignor_mark=0��consignee_mark=1��ѯ
    @Select("SELECT*FROM waybill WHERE user3_id=#{user_id} AND consignor_mark=0 AND `condition`<>3 AND `condition`<>4 AND `condition`<>5 AND invalid=0")
    List<Waybill> getWaybillsByConsignor0(int user_id);

    //����consignee_mark=0��consignor_mark=1��ѯ
    @Select("SELECT*FROM waybill WHERE user2_id=#{user_id} AND consignee_mark=0 AND `condition`<>3 AND `condition`<>4 AND `condition`<>5 AND invalid=0")
    List<Waybill> getWaybillsByConsignee0(int user_id);

    //��consignor_mark��Ϊ2
    @Update("UPDATE waybill SET consignor_mark=2 WHERE id=#{id}")
    void updateConsignorMark2(int id);

    //�޸�consignee_markΪ2
    @Update("UPDATE waybill SET consignee_mark=2 WHERE id=#{id}")
    void updateConsigneeMark2(int id);

    //�޸�waybill��user2_id
    @Update("UPDATE waybill SET user2_id=#{user2_id} WHERE id=#{id}")
    void updateWaybillUser2Id(Map params);

    //�޸�user3_id
    @Update("UPDATE waybill SET user3_id=#{user3_id} WHERE id=#{id}")
    void updateUser3Id(Map params);

    //����user3_id��ѯ���տ��û�н��˵��˵�
    @Select("SELECT*FROM waybill WHERE user3_id=#{user3_id} AND `condition`=3 AND consignor_mark<>2 AND consignee_mark<>2 AND invalid<>1")
    List<Waybill> getWaybillsByUser3Id(int user3_id);

    //��ѯ�����˷ѵ���
    @Select("SELECT*FROM waybill WHERE payway=1 AND time>'2017-05-27 10:54:00' AND consignor_mark=0 AND user3_id=#{user3_id} AND invalid=0 ORDER BY user3_time DESC LIMIT #{m},#{rows}")
    List<Waybill> getFreightReceivableWaybills(Map params);

    //��ѯ���ջ����˵�
    @Select("SELECT*FROM waybill WHERE payway=1 AND consignee_mark=0 AND user2_id=#{user2_id} AND invalid=0")
    List<Waybill> getPriceReceivableWaybills(int user2_id);

    //��ѯ�˷��ѽ��˵��˵�
    @Select("SELECT*FROM waybill WHERE payway=1 AND consignor_mark=2 AND user3_id=#{user3_id} AND invalid=0")
    List<Waybill> getFreightSubmitWaybills(int user3_id);

    //��ѯ�����ѽ��˵��˵�
    @Select("SELECT*FROM waybill WHERE payway=1 AND consignee_mark=2 AND user2_id=#{user2_id} AND invalid=0")
    List<Waybill> getPriceSubmitWaybills(int user2_id);

    //getReceivableWaybillsAndNotSubmit
    //��ѯ���տ��û�н��˵��˵�
//    @Select("SELECT*FROM waybill WHERE (user2_id=#{user2_id} AND `condition`=3 AND payway=0 AND invalid<>1) OR (payway=1 AND consignor_mark=0 AND user3_id=#{user2_id} AND invalid=0) OR (payway=1 AND consignee_mark=0 AND user2_id=#{user2_id} AND invalid=0) LIMIT #{m},#{rows}")
    @Select("SELECT*FROM waybill WHERE (user2_id=#{user2_id} AND `condition`=3 AND payway=0 AND invalid<>1) OR (payway=1 AND consignee_mark=0 AND user2_id=#{user2_id} AND invalid=0) LIMIT #{m},#{rows}")
    List<Waybill> getReceivableWaybillsAndNotSubmit(Map params);

    //getSubmitWaybillsAndNotComplete
    //��ѯ�ѽ��ˣ���û����ɵ��˵�
    @Select("SELECT*FROM waybill WHERE (user2_id=#{user2_id} AND `condition`=4 AND payway=0 AND invalid<>1) OR (payway=1 AND consignor_mark=2 AND user3_id=#{user2_id} AND invalid=0) OR (payway=1 AND consignee_mark=2 AND user2_id=#{user2_id} AND invalid=0) LIMIT #{m},#{rows}")
    List<Waybill> getSubmitWaybillsAndNotComplete(Map params);

    //�޸�consignor_markΪ3
    @Update("UPDATE waybill SET consignor_mark=3 WHERE id=#{id}")
    void updateConsignorMark3(int id);

    //�޸�consignee_markΪ3
    @Update("UPDATE waybill SET consignee_mark=3 WHERE id=#{id}")
    void updateConsigneeMark3(int id);

    //�޸�user2_time
    @Update("UPDATE waybill SET user2_time=#{user2_time} WHERE id=#{id}")
    void updateUser2Time(Map params);

    //�޸�user3_time
    @Update("UPDATE waybill SET user3_time=#{user3_time} WHERE id=#{id}")
    void updateUser3Time(Map params);

    //�������ˣ�������ʱ������
    @Select("(SELECT*,user2_time AS time_all FROM waybill WHERE (user2_id=#{user2_id} AND `condition`=3 AND payway=0 AND invalid<>1) OR" +
            " (payway=1 AND consignee_mark=0 AND user2_id=#{user2_id} AND invalid=0)) " +
            "UNION (SELECT*,user3_time AS time_all FROM waybill WHERE payway=1 AND consignor_mark=0 AND user3_id=#{user2_id} AND invalid=0 AND time<'2017-05-27 10:54:00')" +
            " ORDER BY time_all LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsNotSubmit(Map params);

    //�޸�״̬Ϊ3
    @Update("UPDATE waybill SET `condition`=3,time3=#{time3} WHERE id=#{id}")
    void updateCondition3(Map params);

    //��¼װ�����û�
    @Update("UPDATE waybill SET user1_id=#{user1_id} WHERE id=#{id}")
    void updateUser1Id(Map params);
}
