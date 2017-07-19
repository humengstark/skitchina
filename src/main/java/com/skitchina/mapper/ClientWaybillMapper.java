package com.skitchina.mapper;

import com.skitchina.model.ClientWaybill;
import org.apache.ibatis.annotations.Select;

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
}
