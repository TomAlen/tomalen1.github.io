package com.zwhzzz.service;

import com.zwhzzz.pojo.Grade;
import com.zwhzzz.pojo.clazz;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ClazzService {

    public List<clazz> get_Clazz_List(Map<String,Object> querymap);
    public Integer getCount(Map<String,Object> querymap);
    public int insertClazz (clazz clazz);
    public int updateClazz(clazz clazz);
    public clazz findClazzByName(String name);
    public List<clazz> findAll();
    public int deleteClazz(String ids);


}
