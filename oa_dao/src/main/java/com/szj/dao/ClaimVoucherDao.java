package com.szj.dao;

import com.szj.entity.ClaimVoucher;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("claimVoucherDao")
public interface ClaimVoucherDao {
    void insert(ClaimVoucher claimVoucher);
    void update(ClaimVoucher claimVoucher);
    void delete(int id);
    ClaimVoucher select(int id);

    //查询某个创建者的报销单集合 （创建者编号）
    List<ClaimVoucher> selectByCreateSn(String csn);
    // 查询某个人能够处理的报销单集合 （处理人编号）
    List<ClaimVoucher> selectByNextDealSn(String dsn);
}


