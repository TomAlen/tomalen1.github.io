package com.zwh.service.fore;

import com.zwh.dao.fore.BookOrderDao;
import com.zwh.pojo.fore.BookOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookOrderServiceImpl implements BookOrderService{

    @Autowired
    private BookOrderDao bookOrderDao;

    @Override
    public int insertBookOrder(BookOrder bookorder) {
        return bookOrderDao.insertBookOrder(bookorder);
    }

    @Override
    public int updateBookOrder(BookOrder bookorder) {
        return bookOrderDao.updateBookOrder(bookorder);
    }

    @Override
    public int deleteBookOrder(Integer id) {
        return bookOrderDao.deleteBookOrder(id);
    }

    @Override
    public List<BookOrder> getBookOrderList(Map<String, Object> queryMap) {
        return bookOrderDao.getBookOrderList(queryMap);
    }

    @Override
    public List<BookOrder> getList() {
        return bookOrderDao.getList();
    }

    @Override
    public int getCount(Map<String, Object> queryMap) {
        return bookOrderDao.getCount(queryMap);
    }

    @Override
    public BookOrder findBookOrderByName(String name) {
        return bookOrderDao.findBookOrderByName(name);
    }

    @Override
    public BookOrder findById(Integer id) {
        return bookOrderDao.findById(id);
    }
}
