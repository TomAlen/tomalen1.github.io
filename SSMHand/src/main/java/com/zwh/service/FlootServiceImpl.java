package com.zwh.service;

import com.zwh.dao.FloorDao;
import com.zwh.pojo.Floor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FlootServiceImpl implements FloorService {

    @Autowired
    private FloorDao floorDao;

    @Override
    public int insertFloor(Floor floor) {
        return floorDao.insertFloor(floor);
    }

    @Override
    public int updateFloor(Floor floor) {
        return floorDao.updateFloor(floor);
    }

    @Override
    public int deleteFloor(Integer id) {
        return floorDao.deleteFloor(id);
    }

    @Override
    public List<Floor> getFloorList(Map<String, Object> queryMap) {
        return floorDao.getFloorList(queryMap);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return floorDao.getCount(queryMap);
    }

    @Override
    public List<Floor> getListAll() {
        return floorDao.getListAll();
    }

    @Override
    public Floor findFloorByName(String name) {
        return floorDao.findFloorByName(name);
    }
}
