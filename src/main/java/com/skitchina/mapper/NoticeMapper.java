package com.skitchina.mapper;

import com.skitchina.model.Notice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by hu meng on 2017/6/27.
 */
public interface NoticeMapper {

    //查询最新公告
    @Select("SELECT*FROM notice WHERE id=(SELECT MAX(id) FROM notice)")
    Notice getNewNotice();

    //新增公告
    @Insert("INSERT INTO notice (time,content) VALUES (#{time},#{content})")
    void addNotice(Map params);

    //查询所有公告
    @Select("SELECT*FROM notice ORDER BY time  LIMIT  #{m},#{rows}")
    List<Notice> getAllNotices(Map params);

    //查询公告数量
    @Select("SELECT COUNT(*) FROM notice")
    int getNoticesNum();

    //删除公告
    @Delete("DELETE FROM notice WHERE id=#{id}")
    void deleteNotice(int id);
}
