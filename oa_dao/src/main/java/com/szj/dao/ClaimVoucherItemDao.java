package com.szj.dao;

import com.szj.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/*报销单条目*/
@Repository("claimVoucherItemDao")
public interface ClaimVoucherItemDao {
    void insert(ClaimVoucherItem claimVoucherItem);
    void update(ClaimVoucherItem claimVoucherItem);
    void delete(int id);
    // 查询某一个报销单所附带的信息 （报销单编号）
    List<ClaimVoucherItem> selectByClaimVoucher(int claimVoucherId);
}
