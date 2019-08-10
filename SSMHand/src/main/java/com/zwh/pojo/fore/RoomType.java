package com.zwh.pojo.fore;

import org.springframework.stereotype.Component;

/**
 * 房间类型实体类
 */
@Component
public class RoomType {

    private Integer id;
    private String name;//房型名称
    private String photo;//图片
    private Float price;//价格
    private Integer liveNum;//可住人数
    private Integer bedNum;//床位数
    private Integer roomNum;//房间数
    private Integer avilableNum;//可住或可预订数
    private Integer bookNum;//已预定数
    private Integer livedNum;//已入住人数
    private int status;//房型状态，0：房型已满，1：可预订入住
    private String remark;//备注

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getLiveNum() {
        return liveNum;
    }

    public void setLiveNum(Integer liveNum) {
        this.liveNum = liveNum;
    }

    public Integer getBedNum() {
        return bedNum;
    }

    public void setBedNum(Integer bedNum) {
        this.bedNum = bedNum;
    }

    public Integer getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(Integer roomNum) {
        this.roomNum = roomNum;
    }

    public Integer getAvilableNum() {
        return avilableNum;
    }

    public void setAvilableNum(Integer avilableNum) {
        this.avilableNum = avilableNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getBookNum() {
        return bookNum;
    }

    public void setBookNum(Integer bookNum) {
        this.bookNum = bookNum;
    }

    public Integer getLivedNum() {
        return livedNum;
    }

    public void setLivedNum(Integer livedNum) {
        this.livedNum = livedNum;
    }
}
