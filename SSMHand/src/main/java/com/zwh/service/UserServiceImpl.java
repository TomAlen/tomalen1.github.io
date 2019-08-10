package com.zwh.service;

import com.zwh.dao.UserDao;
import com.zwh.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserDao userDao;

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(String ids) {
        return userDao.deleteUser(ids);
    }

    @Override
    public List<User> getUserList(Map<String, Object> queryMap) {
        return userDao.getUserList(queryMap);
    }

    @Override
    public int getCount(Map<String,Object> queryMap) {
        return userDao.getCount(queryMap);
    }

    @Override
    public int editPassword(User user) {
        return userDao.editPassword(user);
    }
}
