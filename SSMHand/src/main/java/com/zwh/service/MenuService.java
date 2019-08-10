package com.zwh.service;

import com.zwh.pojo.Menu;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface MenuService {

    public int insertMenu(Menu menu);

    public List<Menu> get_Menu_List(Map<String,Object> queryMap);

    public List<Menu> findTopList();

    public int updateMenu(Menu menu);

    public  int deleteId(Integer id);

    public List<Menu> findChildernList(Integer parnetId);

    public int getCount(Map<String,Object> queryMap);

    public int findParentById(Integer id);

    //根据id获取菜单
    public List<Menu> findByMenuList(String ids) ;

}
