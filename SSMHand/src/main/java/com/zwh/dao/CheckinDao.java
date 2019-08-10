package com.zwh.dao;

import com.zwh.pojo.Checkin;
import com.zwh.pojo.fore.BookOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CheckinDao {

    public int insertCheckin(Checkin checkin);
    public int updateCheckin(Checkin checkin);
    public int deleteCheckin(Integer id);
    public List<Checkin> getCheckinList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public Checkin findCheckinByName(String name);
    public Checkin findById(Integer id);
    public List<Checkin> getList();
    public List<Map> getStatsByDay();
    public List<Map> getStatsByMonth();
}
