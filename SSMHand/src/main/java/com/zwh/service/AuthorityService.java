package com.zwh.service;

import com.zwh.pojo.Authority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorityService {

    public int add(Authority authority);
    public int deleteByRoleId(Integer roleId);
    public List<Authority> getListByRoleId(Integer roleId);
}
