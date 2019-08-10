package com.zwh.service;

import com.zwh.dao.RoleDao;
import com.zwh.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public List<Role> get_Role_List(Map<String, Object> queryMap) {
        return roleDao.get_Role_List(queryMap);
    }

    @Override
    public int insertRole(Role role) {
        return roleDao.insertRole(role);
    }

    @Override
    public List<Role> findTopList() {
        return roleDao.findTopList();
    }

    @Override
    public int updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    @Override
    public int deleteId(Integer id) {
        return roleDao.deleteId(id);
    }

    @Override
    public int getCount(Map<String,Object> queryMap) {
        return roleDao.getCount(queryMap);
    }

    @Override
    public List<Role> findList() {
        return roleDao.findList();
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }
}
