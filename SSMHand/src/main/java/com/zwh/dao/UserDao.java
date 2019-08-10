package com.zwh.dao;

import com.zwh.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {

    public User findByUsername(String username);
    public int insertUser(User user);
    public int updateUser(User user);
    public int deleteUser(String ids);
    public List<User> getUserList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public int editPassword(User user);
}
