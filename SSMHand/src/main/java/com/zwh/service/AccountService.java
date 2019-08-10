package com.zwh.service;

import com.zwh.pojo.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AccountService {

    public int insertAccount(Account account);
    public int updateAccount(Account account);
    public int deleteAccount(Integer id);
    public List<Account> getAccountList(Map<String,Object> queryMap);
    public int getCount(Map<String,Object> queryMap);
    public Account getAccountById(Integer id);
    public Account getAccountByName(String name);
    public List<Account> getList();
}
