package com.zwh.service;

import com.zwh.pojo.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RoleService {

    public List<Role> get_Role_List(Map<String,Object> queryMap);
    public int insertRole(Role role);
    public List<Role> findTopList();
    public int updateRole(Role role);
    public  int deleteId(Integer id);
    public int getCount(Map<String,Object> queryMap);
    public List<Role> findList();
    public Role findById(Integer id);
}
