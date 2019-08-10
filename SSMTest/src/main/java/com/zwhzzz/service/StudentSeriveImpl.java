package com.zwhzzz.service;

import com.zwhzzz.dao.StudentDao;
import com.zwhzzz.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentSeriveImpl implements StudentService{

    @Autowired
    StudentDao studentDao;

    @Override
    public List<Student> get_Student_List(Map<String, Object> querymap) {
        return studentDao.get_Student_List(querymap);
    }

    @Override
    public Integer getCount(Map<String, Object> querymap) {
        return studentDao.getCount(querymap);
    }

    @Override
    public int insertStudent(Student student) {
        return studentDao.insertStudent(student);
    }

    @Override
    public int updateStudent(Student student) {
        return studentDao.updateStudent(student);
    }

    @Override
    public Student findStudentByName(String name) {
        return studentDao.findStudentByName(name);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public int deleteStudent(String ids) {
        return studentDao.deleteStudent(ids);
    }

    @Override
    public int updatePassword(Student student) {
        return studentDao.updatePassword(student);
    }
}
