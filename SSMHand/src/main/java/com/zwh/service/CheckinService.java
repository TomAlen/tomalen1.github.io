package com.zwh.service;

import com.zwh.pojo.Checkin;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CheckinService {

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
