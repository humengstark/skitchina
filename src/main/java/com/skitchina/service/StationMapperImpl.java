package com.skitchina.service;

import com.skitchina.mapper.StationMapper;
import com.skitchina.model.Station;
import com.skitchina.model.Waybill;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hu meng on 2017/4/21.
 */
@Service("stationMapperImpl")
public class StationMapperImpl implements StationMapper {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public List<Station> getAllStations() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Station> stations= sqlSession.selectList("com.skitchina.mapper.StationMapper.getAllStations");
        sqlSession.commit();
        sqlSession.close();
        return stations;
    }

    public Station getStationByName(String name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Station station =sqlSession.selectOne("com.skitchina.mapper.StationMapper.getStationByName", name);
        sqlSession.commit();
        sqlSession.close();
        return station;
    }

    public Station getStationById(int id) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Station station =sqlSession.selectOne("com.skitchina.mapper.StationMapper.getStationById", id);
        sqlSession.commit();
        sqlSession.close();
        return station;
    }

    public List<Station> getOtherStations(String name) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Station> stations = sqlSession.selectList("com.skitchina.mapper.StationMapper.getOtherStations", name);
        sqlSession.commit();
        sqlSession.close();
        return stations;
    }

    public void deleteAll1() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.StationMapper.deleteAll1");
        sqlSession.commit();
        sqlSession.close();
    }

    public void deleteAll2() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.StationMapper.deleteAll2");
        sqlSession.commit();
        sqlSession.close();
    }

    public void deleteAll3() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.StationMapper.deleteAll3");
        sqlSession.commit();
        sqlSession.close();
    }

    public void deleteAll4() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.StationMapper.deleteAll4");
        sqlSession.commit();
        sqlSession.close();
    }

    public void deleteAll5() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.delete("com.skitchina.mapper.StationMapper.deleteAll5");
        sqlSession.commit();
        sqlSession.close();
    }
}
