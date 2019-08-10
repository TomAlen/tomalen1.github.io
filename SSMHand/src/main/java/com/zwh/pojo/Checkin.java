package com.zwh.pojo;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Checkin {

    private Integer id;//入住id
    private Integer roomId;//房间id
    private Float checkinPrice;//入住价格
    private Integer roomTypeId;//所属房型id
    private String name;//预定者姓名
    private String realName;//预定着真实姓名
    private String idCard;//预定着身份证号
    private String mobile;//手机号
    private int status;//状态 0：入住中  1：已结算
    private String arriveTime;//入住日期
    private String leaveTime;//离店日期
    private Date createTime;//预定日期
    private Integer bookOrderId;//订单id,可为空
    private String remark;//备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }


    public Float getCheckinPrice() {
        return checkinPrice;
    }

    public void setCheckinPrice(Float checkinPrice) {
        this.checkinPrice = checkinPrice;
    }

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBookOrderId() {
        return bookOrderId;
    }

    public void setBookOrderId(Integer bookOrderId) {
        this.bookOrderId = bookOrderId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
