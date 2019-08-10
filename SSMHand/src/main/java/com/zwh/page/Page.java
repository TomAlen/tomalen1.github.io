package com.zwh.page;

/**
 * 分页工具类
 */
public class Page {

    private int page;//当前页码

    private int rows;//每页显示的数量；

    private int offset;//偏移量，表示从第几个每页的数量开始，比如10  -> 1 ，20 -> 2


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getOffset() {
        //从第几条数据开始
        this.offset = (page-1) * rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
