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
 * ��̨������棬mapper
 */
public interface ManagementMapper {

    //��ѯ�����˵�������״̬��ʱ������
    @Select("SELECT*FROM waybill WHERE waybill_id<>320720000 ORDER BY invalid,`condition`,time DESC,time1 DESC,time2 DESC,time3 DESC,time4 DESC,time5 DESC LIMIT #{m},#{rows}")
    List<Waybill> getAllWaybills(Map params);

    //��ѯ����ҵ��Ա
    @Select("SELECT*FROM user")
    List<User> getAllUsers();

    //��ѯһ���ж����˵�
    @Select("SELECT COUNT(id) FROM waybill")
    int getWaybillNum();

    //����idɾ���˵�
    @Delete("DELETE FROM waybill WHERE id=#{id}")
    void deleteWaybillById(int id);

    //����ID�޸��˵�״̬
    @Update("UPDATE waybill SET `condition`=#{condition} WHERE id=#{id}")
    void updateCondition(Map params);

    //��ѯ�����û�
    @Select("SELECT*FROM user ORDER BY power DESC,id LIMIT #{m},#{rows}")
    List<User> getUsers(Map params);

    //��ѯһ���ж����û�
    @Select("SELECT COUNT(id) FROM user")
    int getUserNum();

    //�޸��û�Ȩ��
    @Update("UPDATE user SET power=#{power} WHERE id=#{id}")
    void updatePower(Map params);

    //����IDɾ���û�
    @Delete("DELETE FROM user WHERE id=#{id}")
    void deleteUserById(int id);

    //����waybill_id��ѯ�˵�
    @Select("SELECT*FROM waybill WHERE waybill_id=#{waybill_id}")
    Waybill getWaybillByWaybillId(int waybill_id);

    //����ID��ѯ�û�
    @Select("SELECT*FROM user WHERE id=#{id}")
    User getUserById(int id);

    //����ID�޸�����
    @Update("UPDATE user SET station=#{station} WHERE id=#{id}")
    void updateStation(Map params);

    //��ѯ��������
    @Select("SELECT*FROM station")
    List<Station> getAllStations();

    //����cellphone��ѯ�û�
    @Select("SELECT*FROM user WHERE cellphone=#{cellphone}")
    User getUserByCellphone(String cellphone);

    //����IDɾ������
    @Delete("DELETE FROM station WHERE id=#{id}")
    void deleteStationById(int id);

    //�������
    @Insert("INSERT INTO station (name,arrive) VALUES (#{name},#{arrive})")
    int addStation(Map params);

    //�ҳ�������ӵ�ID
    @Select("SELECT MAX(id) FROM station")
    int getMaxStationId();

    //�޸�arrive
    @Update("UPDATE station SET arrive=#{arrive} WHERE id=#{id}")
    void updateArriveById(Map params);

    //�޸Ķ���
    @Update("UPDATE waybill SET consignor_company=#{consignor_company},consignor_tel=#{consignor_tel}," +
            "origin=#{origin},consignor_address=#{consignor_address},consignee_company=#{consignee_company}," +
            "consignee_tel=#{consignee_tel},destination=#{destination},consignee_address=#{consignee_address}," +
            "price=#{price},freight=#{freight},number=#{number},remark=#{remark} WHERE waybill_id=#{waybill_id}")
    void updateWaybillByWaybillId(Map params);

    //���϶���
    @Update("UPDATE waybill SET invalid=1 WHERE id=#{id}")
    void invalidWaybillById(int id);

    //�޸�user
    @Update("UPDATE user SET name=#{name},cellphone=#{cellphone},password=#{password}," +
            "company_name=#{company_name},company_address=#{company_address}," +
            "company_tel=#{company_tel},achievement=#{achievement} WHERE id=#{id}")
    void updateUserById(Map params);

    //���ݷ�����ģ�������˵�
    @Select("SELECT*FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%') ORDER BY `condition`,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsignorCompany(Map params);

    //�õ�getWaybillsByConsignorCompany������
    @Select("SELECT COUNT(*) FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%')")
    int getWaybillsByConsignorCompanyNum(Map params);

    //�����ջ�����������
    @Select("SELECT*FROM waybill WHERE consignee_company LIKE CONCAT('%','${consignee_company}','%') ORDER BY `condition`,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsigneeCompany(Map params);

    //�õ�getWaybillsByConsigneeCompany������
    @Select("SELECT COUNT(*) FROM waybill WHERE consignee_company LIKE CONCAT('%','${consignee_company}','%')")
    int getWaybillsByConsigneeCompanyNum(Map params);

    //���ݷ����˺��ջ���һ������
    @Select("SELECT*FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%') AND consignee_company LIKE CONCAT('%','${consignee_company}','%') ORDER BY `condition`,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsByConsignorAndConsignee(Map params);

    //�õ�getWaybillsByConsignorAndConsignee������
    @Select("SELECT COUNT(*) FROM waybill WHERE consignor_company LIKE CONCAT('%','${consignor_company}','%') AND consignee_company LIKE CONCAT('%','${consignee_company}','%')")
    int getWaybillsByConsignorAndConsigneeNum(Map params);

    //�޸�֧����ʽ
    @Select("UPDATE waybill SET payway=#{payway},consignor_mark=1,consignee_mark=1 WHERE waybill_id=#{waybill_id}")
    void updatePayway(Map params);

    //�޸�״̬Ϊ����
    @Update("UPDATE waybill SET `condition`=6 WHERE id=#{id}")
    void endWaybill(int id);

    //����ID��ȡwaybill
    @Select("SELECT*FROM waybill WHERE id=#{id}")
    Waybill getWaybillById(int id);

    //����id�޸��˷Ѻͻ���
    @Update("UPDATE waybill SET freight=#{freight},price=#{price} WHERE id=#{id}")
    void updateFreightOrPrice(Map params);

    //��ѯδ���˵Ķ���
    @Select("SELECT*FROM waybill WHERE `condition`<>4 AND `condition`<>5 AND `condition`<>6 AND invalid=0 ORDER BY `condition` DESC,time DESC LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsNotSubmit(Map params);

    //��ѯδ���˵Ķ���������
    @Select("SELECT COUNT(*) FROM waybill WHERE `condition`<>4 AND `condition`<>5 AND `condition`<>6 AND invalid=0")
    int getWaybillNotSubmitNum();

    //��ѯ���˼�¼
    @Select("SELECT*FROM submit ORDER BY time4 DESC LIMIT #{m},#{rows}")
    List<Submit> getAllSubmits(Map params);

    //��ѯ���˵�����
    @Select("SELECT COUNT(*) FROM submit")
    int getSubmitsNum();

    //��ѯ������δ���˵Ķ���
    @Select("SELECT*FROM waybill WHERE (`condition`=3 AND payway=0 AND invalid<>1) OR (payway=1 AND consignor_mark=0 AND invalid=0) OR (payway=1 AND consignee_mark=0 AND invalid=0) ORDER BY time LIMIT #{m},#{rows}")
    List<Waybill> getWaybillsReceiveAndNotSubmit(Map params);

    //��ѯgetWaybillsReceiveAndNotSubmit������
    @Select("SELECT COUNT(*) FROM waybill WHERE (`condition`=3 AND payway=0 AND invalid<>1) OR (payway=1 AND consignor_mark=0 AND invalid=0) OR (payway=1 AND consignee_mark=0 AND invalid=0)")
    int getWaybillsReceiveAndNotSubmitNum();

    //����id��ѯSubmit
    @Select("SELECT*FROM submit WHERE id=#{id}")
    Submit getSubmitById(int id);
}
