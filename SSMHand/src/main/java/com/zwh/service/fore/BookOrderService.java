package com.zwh.service.fore;

import com.zwh.pojo.fore.BookOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookOrderService {

    public int insertBookOrder(BookOrder bookorder);
    public int updateBookOrder(BookOrder bookorder);
    public int deleteBookOrder(Integer id);
    public List<BookOrder> getBookOrderList(Map<String,Object> queryMap);
    public List<BookOrder> getList();
    public int getCount(Map<String,Object> queryMap);
    public BookOrder findBookOrderByName(String name);
    public BookOrder findById(Integer id);
}
