package com.zwh.service;

import com.zwh.dao.AccountDao;
import com.zwh.pojo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public int insertAccount(Account account) {
        return accountDao.insertAccount(account);
    }

    @Override
    public int updateAccount(Account account) {
        return accountDao.updateAccount(account);
    }

    @Override
    public int deleteAccount(Integer id) {
        return accountDao.deleteAccount(id);
    }

    @Override
    public List<Account> getAccountList(Map<String, Object> queryMap) {
        return accountDao.getAccountList(queryMap);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return accountDao.getCount(queryMap);
    }

    @Override
    public Account getAccountById(Integer id) {
        return accountDao.getAccountById(id);
    }

    @Override
    public Account getAccountByName(String name) {
        return accountDao.getAccountByName(name);
    }

    @Override
    public List<Account> getList() {
        return accountDao.getList();
    }


}
