package com.zwh.dao;

import com.zwh.pojo.Floor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FloorDao {

    public int insertFloor(Floor floor);
    public int updateFloor(Floor floor);
    public int deleteFloor(Integer id);
    public List<Floor> getFloorList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public List<Floor> getListAll();
    public Floor findFloorByName(String name);
}
