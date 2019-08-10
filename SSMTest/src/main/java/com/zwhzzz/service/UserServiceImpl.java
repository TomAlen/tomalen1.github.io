package com.zwhzzz.service;

import com.zwhzzz.dao.UserDao;
import com.zwhzzz.pojo.User;
import com.zwhzzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    //将UserDao通过AutoWire注入
    @Autowired
    private UserDao Userdao;

    @Override
    public User findUserByUserName(String username) {
        return Userdao.findUserByUserName(username);
    }

    @Override
    public int addUser(User user) {
        return Userdao.addUser(user);
    }

    @Override
    public int delete(String ids) {
        return Userdao.delete(ids);
    }

    @Override
    public List<User> get_User_List(Map<String, Object> querymap) {
        return Userdao.get_User_List(querymap);
    }

    @Override
    public Integer getCount(Map<String, Object> querymap) {
        return Userdao.getCount(querymap);
    }

    @Override
    public int updateUser(User user) {
        return Userdao.updateUser(user);
    }

    @Override
    public int updatePassword(User user) {
        return Userdao.updatePassword(user);
    }
}
