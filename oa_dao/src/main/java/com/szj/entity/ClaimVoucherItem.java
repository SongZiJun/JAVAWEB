package com.szj.entity;


/*
* 报销单条目
* */
public class ClaimVoucherItem {
    /*
    * 编号*/
    private Integer id;

    /*报销单编号
    * 用来关联报销单的
    * */
    private Integer claimVoucherId;


    private String item;    /*费用类型*/

    private Double amount;  /*金额*/

    private String comment; /*说明*/

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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}