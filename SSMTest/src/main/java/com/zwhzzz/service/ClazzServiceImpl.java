package com.zwhzzz.service;

import com.zwhzzz.dao.ClazzDao;
import com.zwhzzz.pojo.clazz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzDao clazzDao;

    @Override
    public List<clazz> get_Clazz_List(Map<String, Object> querymap) {
        return clazzDao.get_Clazz_List(querymap);
    }

    @Override
    public Integer getCount(Map<String, Object> querymap) {
        return clazzDao.getCount(querymap);
    }

    @Override
    public int insertClazz(clazz clazz) {
        return clazzDao.insertClazz(clazz);
    }

    @Override
    public int updateClazz(clazz clazz) {
        return clazzDao.updateClazz(clazz);
    }

    @Override
    public clazz findClazzByName(String name) {
        return clazzDao.findClazzByName(name);
    }

    @Override
    public List<clazz> findAll() {
        return clazzDao.findAll();
    }

    @Override
    public int deleteClazz(String ids) {
        return clazzDao.deleteClazz(ids);
    }

}
