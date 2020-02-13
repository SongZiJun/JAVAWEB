package com.szj.service.impl;


import com.szj.dao.ClaimVoucherDao;
import com.szj.dao.ClaimVoucherItemDao;
import com.szj.dao.DealRecordDao;
import com.szj.dao.EmployeeDao;
import com.szj.entity.ClaimVoucher;
import com.szj.entity.ClaimVoucherItem;
import com.szj.entity.DealRecord;
import com.szj.entity.Employee;
import com.szj.global.Contant;
import com.szj.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service("cliamVoucherService")
public class ClaimVoucherServiceImpl implements ClaimVoucherService {

    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Autowired
    private DealRecordDao dealRecordDao;
    // 拿到报销单当前的创建人需要用
    @Autowired
    private EmployeeDao employeeDao;

    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> claimVoucherItems) {
        //设置创建时间
        claimVoucher.setCreateTime(new Date());
        //设置待处理人 创建者本人
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        //设置新创建报销单的状态  在常量类中设置新创建的状态
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);

        //insert会自动为这个对象的 id 赋值
        //后面插入item的时候 item 需要用到这个 id 所以要写下面的循环
        claimVoucherDao.insert(claimVoucher);

        // ietm 去接受集合中迭代出来的每一个元素
        for(ClaimVoucherItem item:claimVoucherItems){
            /*获取上面生成的id，放到 item 对象中
            一共五个属性其他属性（金额 类型 备注）都是页面提交的
            只缺少报销单编号
            */
            item.setClaimVoucherId(claimVoucher.getId());
            //保存报销单的编号
            claimVoucherItemDao.insert(item);

        }

    }
    /*
    * 获取报销单对象
    * */
    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    /*获取报销单对应的条目*/
    public List<ClaimVoucherItem> getclaimVoucherItems(int claimVoucherId) {
        return claimVoucherItemDao.selectByClaimVoucher(claimVoucherId);
    }

    /*获取报销单日志信息*/
    public List<DealRecord> getRecords(int claimVoucherId) {
        return dealRecordDao.selectByClaimVoucher(claimVoucherId);
    }
    /*
    * 个人报销单
    * */
    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }
    /*
    * 待处理报销单
    * */
    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }


    /*修改报销单*/
    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> Items) {
        // 待处理人还是创建者本身 状态还是新创建状态
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_CREATED);
        claimVoucherDao.update(claimVoucher);   //更新对象

        /*
        * 更新条目集合三个注意点
        * 1.删除准备不要的条目
        * 2.修改已经有的条目
        * 3.插入数据库没有的新条目
        * */


        //获取数据库中已有的集合
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());
        for(ClaimVoucherItem old:olds){
            //判断当前获取的条目是不是不存在在这个集合内
            boolean isHave = false;
            //迭代现有的准备更新的条目
            for(ClaimVoucherItem item:Items){
                if(item.getId() == old.getId()){
                    isHave = true;
                    break;
                }
            }
            //在新的集合中没有找到
            if(!isHave){
                claimVoucherItemDao.delete(old.getId());
            }

            for(ClaimVoucherItem item:Items){
                if (item.getId() > 0){
                    claimVoucherItemDao.update(item);
                }else {
                    claimVoucherItemDao.insert(item);
                }
            }

        }


    }
    /*提交报销单*/
    public void submit(int id) {
        // 拿到报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        // 拿到报销单当前的创建人  //编号是报销单的创建者
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());

        //状态改为待审核
        claimVoucher.setStatus(Contant.CLAIMVOUCHER_SUBMIT);
        //处理人编号：跟员工同部门的 职位是部门经理的那个人
        //get(0).getSn() 获取第一个  的编号
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(),Contant.POST_FM).get(0).getSn());
        claimVoucherDao.update(claimVoucher);

        //进行记录的保存
        DealRecord dealRecord = new DealRecord();
        dealRecord.setDealWay(Contant.DEAL_SUBMIT);//处理方式
        dealRecord.setDealSn(employee.getSn());//处理人编号
        dealRecord.setClaimVoucherId(id);//处理报销单编号
        dealRecord.setDealResult(Contant.CLAIMVOUCHER_SUBMIT);//处理结果：当前报销单的状态
        dealRecord.setDealTime(new Date());//处理时间
        dealRecord.setComment("无");//备注
        //记录保存
        dealRecordDao.insert(dealRecord);
    }
    //处理报销单
    public void deal(DealRecord dealRecord) {
        //拿出当前报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        //拿出当前处理人
        Employee employee = employeeDao.select(dealRecord.getDealSn());

        //根据不同的操作来处理不同的业务
        //如果它的处理方式是审核通过
        if(dealRecord.getDealWay().equals(Contant.DEAL_PASS)){
            //如果金额小于等于限额5000  处理人是总经理
            if(claimVoucher.getTotalAmount() <= Contant.LIMIT_CHECK || employee.getPost().equals(Contant.POST_GM)){
                //当前审核处理人如果是总经理 - 直接打款
                    claimVoucher.setStatus(Contant.CLAIMVOUCHER_APPROVED); //修改报销单状态：已审核

                    //修改待处理人：职位是财务的人    selectByDepartmentAndPost返回的是集合   .get(0).getSn()拿出第一个然后拿出编号
                    claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_CASHIER).get(0).getSn());
                    /*
                     修改处理记录 DealRecord
                    * */
                     //修改处理时间 dealTime
                    dealRecord.setDealTime(new Date());
                    //修改处理结果 dealResult - 报销单状态：已审核
                    dealRecord.setDealResult(Contant.CLAIMVOUCHER_APPROVED);
            }else {
                //需要复审的，
                //修改报销单状态：待复审
                claimVoucher.setStatus(Contant.CLAIMVOUCHER_RECHECK);
                //修改待处理人：总经理
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null,Contant.POST_GM).get(0).getSn());
                //修改记录处理时间
                dealRecord.setDealTime(new Date());
                //修改记录处理结果:待复审
                dealRecord.setDealResult(Contant.CLAIMVOUCHER_RECHECK);
            }
        }else if (dealRecord.getDealWay().equals(Contant.DEAL_BACK)){
            //否则如果处理方式是打回操作

            //修改报销单状态：已打回
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_BACK);
            //修改待处理人：报销单的创建者
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
            //修改记录处理时间
            dealRecord.setDealTime(new Date());
            //修改记录处理结果:已打回
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_BACK);
        }else if(dealRecord.getDealWay().equals(Contant.DEAL_REJECT)){
            //如果是拒绝
            //报销单状态 终止
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_TERMINATED);
            //待处理人 没有了 设置为空
            claimVoucher.setNextDealSn(null);
            //修改处理记录时间：为当前时间
            dealRecord.setDealTime(new Date());
            //修改处理结果：已终止
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_TERMINATED);
        }else if (dealRecord.getDealWay().equals(Contant.DEAL_PAID)){
            //如果是打款
            //修改报销单状态为：已打款
            claimVoucher.setStatus(Contant.CLAIMVOUCHER_PAID);
            //报销单待处理人 没了
            claimVoucher.setNextDealSn(null);
            //修改记录处理时间和处理结果
            dealRecord.setDealTime(new Date());
            dealRecord.setDealResult(Contant.CLAIMVOUCHER_PAID);
        }


        //报销单修改
        claimVoucherDao.update(claimVoucher);
        //增加处理记录
        dealRecordDao.insert(dealRecord);


        /*
        不需要复审的，

            当前审核处理人如果是总经理 - 直接打款
                修改报销单状态：已审核
                待处理人：职位是财务
              修改处理记录 DealRecord
                修改处理时间 dealTime
                修改处理结果 dealResult
         需要复审的，
              修改报销单状态：待复审
              修改待处理人：总经理
              修改处理时间
              修改处理结果
         */




        /*
        * 打回操作
        * 设置报销单状态：已打回
        * 设置待处理人：当前报销单的创建者
        *  修改处理状态DealRecord
        * */


        /*
        * 拒绝操作
        * 设置报销单状态：终止
        * 设置待处理人：没有待处理人NULL
        * 修改处理状态DealRecord
        * */



        /*
        * 打款操作
        * 设置报销单状态：已打款
        * 设置待处理人：也没有了 设置为空
        * 修改处理状态DealRecord
        * */


    }


}
