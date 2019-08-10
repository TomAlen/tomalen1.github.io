package com.zwh.pojo;

import org.springframework.stereotype.Component;

/**
 * 菜单实体类
 */

@Component
public class Menu {

    private Integer id;
    private String name;
    private Integer parentId;//父类id
    private Integer _parentId;//用来匹配easyui的父类id
    private String url;//点击后的url
    private String icon;//菜单icon图标



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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Integer get_parentId() {
        _parentId = parentId;
        return _parentId;
    }

    public void set_parentId(Integer _parentId) {
        this._parentId = _parentId;
    }
}
