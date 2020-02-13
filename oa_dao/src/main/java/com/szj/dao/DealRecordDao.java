package com.szj.dao;

import com.szj.entity.DealRecord;
import org.springframework.stereotype.Repository;

/*
处理记录  类似日志
* 没有删除和修改功能
* */
import java.util.List;
@Repository("dealRecordDao")
public interface DealRecordDao {
    void insert(DealRecord dealRecord);
    // 针对于某个报销单，我想查看它的处理流程
    List<DealRecord> selectByClaimVoucher(int cvid);
}
