package com.zwh.service;

import com.zwh.dao.AuthorityDao;
import com.zwh.pojo.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    AuthorityDao authorityDao;

    @Override
    public int add(Authority authority) {
        return authorityDao.add(authority);
    }

    @Override
    public int deleteByRoleId(Integer roleId) {
        return authorityDao.deleteByRoleId(roleId);
    }

    @Override
    public List<Authority> getListByRoleId(Integer roleId) {
        return authorityDao.getListByRoleId(roleId);
    }
}
