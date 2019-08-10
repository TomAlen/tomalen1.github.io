package com.zwh.service;

import com.zwh.dao.LogDao;
import com.zwh.pojo.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    public List<Log> getList(Map<String, Object> queryMap) {
        return logDao.getList(queryMap);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return logDao.getCount(queryMap);
    }

    @Override
    public int insertLog(Log log) {
        return logDao.insertLog(log);
    }

    @Override
    public int deleteLog(String ids) {
        return logDao.deleteLog(ids);
    }

    @Override
    public int insertLog(String content) {
        Log log = new Log();
        log.setContent(content);
        log.setCreateTime(new Date());
        return logDao.insertLog(log);
    }
}
