package com.szj.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/*
* 处理记录
* */
public class DealRecord {
    private Integer id;

    //需要页面提交过来的
    private Integer claimVoucherId; /*报销单编号*/


    private String dealSn;  /*处理人*/
    /*处理人肯定不能显示编号，所以需要声明处理人对象*/
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date dealTime;  /*处理时间*/
    //需要页面提交过来的
    private String dealWay; /*处理方式*/

    private String dealResult;  /*处理结果*/
    //需要页面提交过来的
    private String comment;     /*处理意见*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClaimVoucherId() {
        return claimVoucherId;
    }

    public void setClaimVoucherId(Integer claimVoucherId) {
        this.claimVoucherId = claimVoucherId;
    }

    public String getDealSn() {
        return dealSn;
    }

    public void setDealSn(String dealSn) {
        this.dealSn = dealSn;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealWay() {
        return dealWay;
    }

    public void setDealWay(String dealWay) {
        this.dealWay = dealWay;
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private Employee dealer;

    public Employee getDealer() {
        return dealer;
    }

    public void setDealer(Employee dealer) {
        this.dealer = dealer;
    }
}