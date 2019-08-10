package com.zwh.service;

import com.zwh.dao.MenuDao;
import com.zwh.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuDao menuDao;

    @Override
    public int insertMenu(Menu menu) {
        return menuDao.insertMenu(menu);
    }

    @Override
    public List<Menu> get_Menu_List(Map<String, Object> queryMap) {
        return menuDao.get_Menu_List(queryMap);
    }

    @Override
    public List<Menu> findTopList() {
        return menuDao.findTopList();
    }

    @Override
    public int updateMenu(Menu menu) {
        return menuDao.updateMenu(menu);
    }

    @Override
    public int deleteId(Integer id) {
        return menuDao.deleteId(id);
    }

    @Override
    public List<Menu> findChildernList(Integer parnetId) {
        return menuDao.findChildernList(parnetId);
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return menuDao.getCount(queryMap);
    }

    @Override
    public int findParentById(Integer id) {
        return menuDao.findParentById(id);
    }

    @Override
    public List<Menu> findByMenuList(String ids) {
        return menuDao.findByMenuList(ids);
    }
}
