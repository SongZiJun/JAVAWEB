package com.szj.service;


import com.szj.entity.ClaimVoucher;
import com.szj.entity.ClaimVoucherItem;
import com.szj.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherService {
    //填写报销单
    /*报销单实体对象，报销单条目*/
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems);
    //查看报销单对象信息
    ClaimVoucher get(int id);

    //查看报销单对应的所有的条目  根据claimVoucherId报销单编号
    List<ClaimVoucherItem> getclaimVoucherItems(int claimVoucherId);

    //查看报销单的审核记录
    List<DealRecord> getRecords(int claimVoucherId);


    // 获取个人报销单
    List<ClaimVoucher> getForSelf(String sn);
    // 获取待处理报销单
    List<ClaimVoucher> getForDeal(String sn);

    // 修改报销单
    void update(ClaimVoucher claimVoucher,List<ClaimVoucherItem> Items);
    //提交报销单
    void submit(int id);

    //处理报销单:通过审核记录来获取
    void  deal(DealRecord dealRecord);
}
