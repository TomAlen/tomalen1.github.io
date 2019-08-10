package com.zwh.service;

import com.zwh.pojo.Log;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface LogService {

    public List<Log> getList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public int insertLog(Log log);
    public int deleteLog(String ids);
    public int insertLog(String content);
}
