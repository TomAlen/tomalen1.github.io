package com.zwh.service.fore;

import com.zwh.dao.fore.RoomTypeDao;
import com.zwh.pojo.fore.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

    @Autowired
    private RoomTypeDao roomTypeDao;

    @Override
    public int insertRoomType(RoomType roomType) {
        return roomTypeDao.insertRoomType(roomType);
    }

    @Override
    public int updateRoomType(RoomType roomType) {
        return roomTypeDao.updateRoomType(roomType);
    }

    @Override
    public int deleteRoomType(Integer id) {
        return roomTypeDao.deleteRoomType(id);
    }

    @Override
    public List<RoomType> getRoomTypeList(Map<String, Object> queryMap) {
        return roomTypeDao.getRoomTypeList(queryMap);
    }

    @Override
    public List<RoomType> getList() {
        return roomTypeDao.getList();
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return roomTypeDao.getCount(queryMap);
    }

    @Override
    public RoomType findRoomTypeByName(String name) {
        return roomTypeDao.findRoomTypeByName(name);
    }

    @Override
    public RoomType findById(Integer id) {
        return roomTypeDao.findById(id);
    }

    @Override
    public void updateNum(RoomType roomType) {
        roomTypeDao.updateNum(roomType);
    }

    @Override
    public void updateStatus(int status) {
        roomTypeDao.updateStatus(status);
    }
}
