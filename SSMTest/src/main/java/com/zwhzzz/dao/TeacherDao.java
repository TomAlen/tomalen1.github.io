package com.zwhzzz.dao;


import com.zwhzzz.pojo.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TeacherDao {

    public List<Teacher> get_Teacher_List(Map<String,Object> querymap);
    public Integer getCount(Map<String,Object> querymap);
    public int insertTeacher (Teacher teacher);
    public int updateTeacher(Teacher teacher);
    public Teacher findTeacherByName(String name);
    public List<Teacher> findAll();
    public int deleteTeacher(String ids);
    public int updatePassword(Teacher teacher);

}
