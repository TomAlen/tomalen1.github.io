package com.zwhzzz.service;

import com.zwhzzz.pojo.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface StudentService {
    public List<Student> get_Student_List(Map<String,Object> querymap);
    public Integer getCount(Map<String,Object> querymap);
    public int insertStudent (Student student);
    public int updateStudent(Student student);
    public Student findStudentByName(String name);
    public List<Student> findAll();
    public int deleteStudent(String ids);
    public int updatePassword(Student student);
}
