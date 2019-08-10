package com.zwhzzz.service;

import com.zwhzzz.dao.GradeDao;
import com.zwhzzz.pojo.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService{

    @Autowired
    private GradeDao gradeDao;

    @Override
    public List<Grade> get_Grade_List(Map<String, Object> querymap) {
        return gradeDao.get_Grade_List(querymap);
    }

    @Override
    public Integer getCount(Map<String, Object> querymap) {
        return gradeDao.getCount(querymap);
    }

    @Override
    public int insertGrade(Grade grade) {
        return gradeDao.insertGrade(grade);
    }

    @Override
    public Grade findGradeByName(String name) {
        return gradeDao.findGradeByName(name);
    }

    @Override
    public int updateGrade(Grade grade) {
        return gradeDao.updateGrade(grade);
    }

    @Override
    public int deleteGrade(String ids) {
        return gradeDao.deleteGrade(ids);
    }

    @Override
    public List<Grade> findAll() {
        return gradeDao.findAll();
    }
}
