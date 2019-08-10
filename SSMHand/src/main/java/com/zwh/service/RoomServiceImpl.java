package com.zwh.service;

import com.zwh.dao.RoomDao;
import com.zwh.pojo.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Override
    public int insertRoom(Room room) {
        return roomDao.insertRoom(room);
    }

    @Override
    public int updateRoom(Room room) {
        return roomDao.updateRoom(room);
    }

    @Override
    public int deleteRoom(Integer id) {
        return roomDao.deleteRoom(id);
    }

    @Override
    public List<Room> getRoomList(Map<String, Object> queryMap) {
        return roomDao.getRoomList(queryMap);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return roomDao.getCount(queryMap);
    }

    @Override
    public Room getRoomById(Integer id) {
        return roomDao.getRoomById(id);
    }

    @Override
    public Room getRoomBySn(String sn) {
        return roomDao.getRoomBySn(sn);
    }

    @Override
    public List<Room> getList() {
        return roomDao.getList();
    }


}
