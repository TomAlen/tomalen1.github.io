package com.zwh.dao.fore;

import com.zwh.pojo.fore.BookOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookOrderDao {

    public int insertBookOrder(BookOrder bookorder);
    public int updateBookOrder(BookOrder bookorder);
    public int deleteBookOrder(Integer id);
    public List<BookOrder> getBookOrderList(Map<String,Object> queryMap);
    public List<BookOrder> getList();
    public int getCount(Map<String,Object> queryMap);
    public BookOrder findBookOrderByName(String name);
    public BookOrder findById(Integer id);

}
