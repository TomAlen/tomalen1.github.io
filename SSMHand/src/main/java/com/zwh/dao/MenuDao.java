package com.zwh.dao;

import com.zwh.pojo.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MenuDao {

    public List<Menu>  get_Menu_List(Map<String,Object> queryMap);
    public int insertMenu(Menu menu);
    public List<Menu> findTopList();
    public int updateMenu(Menu menu);
    public  int deleteId(Integer id);
    public List<Menu> findChildernList(Integer parnetId);
    public int getCount(Map<String,Object> queryMap);
    public int findParentById(Integer id);
    public List<Menu> findByMenuList(String ids) ;
}
