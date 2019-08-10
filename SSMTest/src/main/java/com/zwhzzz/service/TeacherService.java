package com.zwhzzz.service;

import com.zwhzzz.pojo.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface TeacherService {


    public List<Teacher> get_Teacher_List(Map<String,Object> querymap);
    public Integer getCount(Map<String,Object> querymap);
    public int insertTeacher (Teacher teacher);
    public int updateTeacher(Teacher teacher);
    public Teacher findTeacherByName(String name);
    public List<Teacher> findAll();
    public int deleteTeacher(String ids);
    public int updatePassword(Teacher teacher);
}
