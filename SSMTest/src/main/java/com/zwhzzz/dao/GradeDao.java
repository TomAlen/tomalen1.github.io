package com.zwhzzz.dao;

import com.zwhzzz.pojo.Grade;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface GradeDao {

    public List<Grade> get_Grade_List(Map<String,Object> querymap);
    public Integer getCount(Map<String,Object> querymap);
    public int insertGrade(Grade grade);
    public Grade findGradeByName(String name);
    public int updateGrade(Grade grade);
    public int deleteGrade(String ids);
    public List<Grade> findAll();

}
