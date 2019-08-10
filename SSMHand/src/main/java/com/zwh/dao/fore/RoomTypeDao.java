package com.zwh.dao.fore;

import com.zwh.pojo.fore.RoomType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoomTypeDao {

    public int insertRoomType(RoomType roomType);
    public int updateRoomType(RoomType roomType);
    public int deleteRoomType(Integer id);
    public List<RoomType> getRoomTypeList(Map<String,Object> queryMap);
    public List<RoomType> getList();
    public int getCount(Map<String,Object> queryMap);
    public RoomType findRoomTypeByName(String name);
    public RoomType findById(Integer id);
    //此方法用于预定订单的变化，对可预定数、已预定数、已入住人数进行更新
    public void updateNum(RoomType roomType);
    public void updateStatus(int status);

}
