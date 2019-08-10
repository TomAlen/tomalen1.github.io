package com.zwh.pojo;

import org.springframework.stereotype.Component;

@Component
public class Floor {

    private Integer id;//楼层id

    private String name;//楼层名称

    private String remark;//楼层备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
