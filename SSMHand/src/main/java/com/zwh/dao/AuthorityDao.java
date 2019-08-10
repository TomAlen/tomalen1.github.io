package com.zwh.dao;

import com.zwh.pojo.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityDao {

    public int add(Authority authority);
    public int deleteByRoleId(Integer roleId);
    public List<Authority> getListByRoleId(Integer roleId);

}
