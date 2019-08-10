package com.zwhzzz.service;


import com.zwhzzz.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    public User findUserByUserName(String username);
    public int addUser(User user);
    public int delete(String ids);
    public List<User> get_User_List(Map<String,Object> querymap);
    public Integer getCount(Map<String,Object> querymap);
    public int updateUser(User user);
    public int updatePassword(User user);
}
