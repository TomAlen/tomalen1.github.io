package com.zwhzzz.page;

public class page {

    private int page;//代表当前页
    private  int rows;//代表一页多少条数据
    private int offset;//表示从第几页开始

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
        this.offset = (page-1)*rows;
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = (page-1)*rows;
    }
}
