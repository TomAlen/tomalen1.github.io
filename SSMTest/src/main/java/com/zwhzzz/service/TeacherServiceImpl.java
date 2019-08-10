package com.zwhzzz.service;

import com.zwhzzz.dao.TeacherDao;
import com.zwhzzz.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    TeacherDao teacherDao;

    @Override
    public List<Teacher> get_Teacher_List(Map<String, Object> querymap) {
        return teacherDao.get_Teacher_List(querymap);
    }

    @Override
    public Integer getCount(Map<String, Object> querymap) {
        return teacherDao.getCount(querymap);
    }

    @Override
    public int insertTeacher(Teacher teacher) {
        return teacherDao.insertTeacher(teacher);
    }

    @Override
    public int updateTeacher(Teacher teacher) {
        return teacherDao.updateTeacher(teacher);
    }

    @Override
    public Teacher findTeacherByName(String name) {
        return teacherDao.findTeacherByName(name);
    }

    @Override
    public List<Teacher> findAll() {
        return teacherDao.findAll();
    }

    @Override
    public int deleteTeacher(String ids) {
        return teacherDao.deleteTeacher(ids);
    }

    @Override
    public int updatePassword(Teacher teacher) {
        return teacherDao.updatePassword(teacher);
    }
}
