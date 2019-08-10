package com.zwh.service;

import com.zwh.dao.CheckinDao;
import com.zwh.pojo.Checkin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CheckinServiceImpl implements CheckinService{

    @Autowired
    private CheckinDao checkinDao;


    @Override
    public int insertCheckin(Checkin checkin) {
        return checkinDao.insertCheckin(checkin);
    }

    @Override
    public int updateCheckin(Checkin checkin) {
        return checkinDao.updateCheckin(checkin);
    }

    @Override
    public int deleteCheckin(Integer id) {
        return checkinDao.deleteCheckin(id);
    }

    @Override
    public List<Checkin> getCheckinList(Map<String, Object> queryMap) {
        return checkinDao.getCheckinList(queryMap);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return checkinDao.getCount(queryMap);
    }

    @Override
    public Checkin findCheckinByName(String name) {
        return checkinDao.findCheckinByName(name);
    }

    @Override
    public Checkin findById(Integer id) {
        return checkinDao.findById(id);
    }

    @Override
    public List<Checkin> getList() {
        return checkinDao.getList();
    }

    @Override
    public List<Map> getStatsByDay() {
        return checkinDao.getStatsByDay();
    }

    @Override
    public List<Map> getStatsByMonth() {
        return checkinDao.getStatsByMonth();
    }
}
