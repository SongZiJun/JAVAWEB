package com.szj.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
* 报销单
* */
public class ClaimVoucher {
    private Integer id;     //编号

    private String cause;   /*原因*/

    private String createSn;    /*创建者编号*/

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date createTime;    /*创建时间*/

    private String nextDealSn;  /*待处理人编号*/

    /*
    * 创建者编号和待处理人编号不可以直接在表现层实现
    * 所以要通过 Employee 对象来获取
    * */
    private Double totalAmount;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCreateSn() {
        return createSn;
    }

    public void setCreateSn(String createSn) {
        this.createSn = createSn;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNextDealSn() {
        return nextDealSn;
    }

    public void setNextDealSn(String nextDealSn) {
        this.nextDealSn = nextDealSn;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Employee creater;   /*创建者对象*/
    private Employee dealer;    /*处理人对象*/

    public Employee getCreater() {
        return creater;
    }

    public void setCreater(Employee creater) {
        this.creater = creater;
    }

    public Employee getDealer() {
        return dealer;
    }

    public void setDealer(Employee dealer) {
        this.dealer = dealer;
    }
}