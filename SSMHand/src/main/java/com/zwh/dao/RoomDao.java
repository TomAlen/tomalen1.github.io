package com.zwh.dao;

import com.zwh.pojo.Room;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoomDao {

    public int insertRoom(Room room);
    public int updateRoom(Room room);
    public int deleteRoom(Integer id);
    public List<Room> getRoomList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public Room getRoomById(Integer id);
    public Room getRoomBySn(String sn);
    public List<Room> getList();
}
