package com.zwh.service;

import com.zwh.pojo.Floor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FloorService {

    public int insertFloor(Floor floor);
    public int updateFloor(Floor floor);
    public int deleteFloor(Integer id);
    public List<Floor> getFloorList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public List<Floor> getListAll();
    public Floor findFloorByName(String name);
}
