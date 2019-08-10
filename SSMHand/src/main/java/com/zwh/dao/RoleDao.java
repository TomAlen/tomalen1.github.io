package com.zwh.dao;

import com.zwh.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface RoleDao {
    public List<Role> get_Role_List(Map<String,Object> queryMap);
    public int insertRole(Role role);
    public List<Role> findTopList();
    public int updateRole(Role role);
    public  int deleteId(Integer id);
    public int getCount(Map<String,Object> queryMap);
    public List<Role> findList();
    public Role findById(Integer id);
}
