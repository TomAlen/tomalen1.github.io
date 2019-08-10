package com.zwhzzz.service;

import com.zwhzzz.pojo.Grade;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public interface GradeService {

    public List<Grade> get_Grade_List(Map<String,Object> querymap);
    public Integer getCount(Map<String,Object> querymap);
    public int insertGrade(Grade grade);
    public Grade findGradeByName(String name);
    public int updateGrade(Grade grade);
    public int deleteGrade(String ids);
    public List<Grade> findAll();

}
