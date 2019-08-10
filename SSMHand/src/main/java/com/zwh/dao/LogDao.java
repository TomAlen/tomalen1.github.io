package com.zwh.dao;

import com.zwh.pojo.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LogDao {

    public List<Log> getList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public int insertLog(Log log);
    public int deleteLog(String ids);

}
