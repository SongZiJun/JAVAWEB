package com.szj.dto;

import com.szj.entity.ClaimVoucher;
import com.szj.entity.ClaimVoucherItem;

import java.util.List;

/*声明要收集的数据*/
public class ClaimVoucherInfo {
    //要收集报销单对象的基本信息 和 一系列条目信息
    private ClaimVoucher claimVoucher;
    private List<ClaimVoucherItem> items;

    public ClaimVoucher getClaimVoucher() {
        return claimVoucher;
    }

    public void setClaimVoucher(ClaimVoucher claimVoucher) {
        this.claimVoucher = claimVoucher;
    }

    public List<ClaimVoucherItem> getItems() {
        return items;
    }

    public void setItems(List<ClaimVoucherItem> items) {
        this.items = items;
    }
}
