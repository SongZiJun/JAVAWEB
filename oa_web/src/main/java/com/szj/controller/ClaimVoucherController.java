package com.szj.controller;

import com.szj.dto.ClaimVoucherInfo;
import com.szj.entity.ClaimVoucher;
import com.szj.entity.DealRecord;
import com.szj.entity.Employee;
import com.szj.global.Contant;
import com.szj.service.ClaimVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.Map;
@Controller("claimVoucherController")
@RequestMapping("/claim_voucher")
public class ClaimVoucherController {

    // 注入业务处理对象
    @Autowired
    private ClaimVoucherService claimVoucherService;

    /*去添加*/
    @RequestMapping("/to_add")
    public String toAdd(Map<String,Object> map){
        //页面需要所以的数据类型
        map.put("items",Contant.getItems());  //放置常量类中的所有费用类型
        // 构造ClaimVoucherInfo对象 dto要收集的信息
        map.put("info",new ClaimVoucherInfo());
        return "claim_voucher_add";
    }
    //完成添加
    /*
    * add 需要接收值，而要接收的值是封装到 ClaimVoucherInfo 对象中的
    * ClaimVoucherInfo info 要和上面去添加保存的键名一致
    * */
    @RequestMapping("/add")
    public String add(HttpSession session, ClaimVoucherInfo info){
        //找出登录时的信息
        Employee employee = (Employee)session.getAttribute("employee");
        //将报销单创建者的编号 设置为 当前登录用户的编号
        info.getClaimVoucher().setCreateSn(employee.getSn());
        // 对（报销单信息  报销单条目信息）保存
        claimVoucherService.save(info.getClaimVoucher(),info.getItems());
        return "redirect:deal"; //重定向到待处理
    }

    /*
    * 向页面传递信息
    * */
    @RequestMapping("/detail")
    // 接收用户传递过来的用户编号
    public String detail(int id, Map<String,Object> map){
        // 报销单的详细信息
        map.put("claimVoucher",claimVoucherService.get(id));
        // 报销单的附属信息
        map.put("items",claimVoucherService.getclaimVoucherItems(id));
        // 报销单的日志信息
        map.put("records",claimVoucherService.getRecords(id));
        return "claim_voucher_detail";
    }

    /*
    * 个人报销单
    * */
    @RequestMapping("/self")
    public String self(HttpSession httpSession,Map<String,Object>map){
        Employee employee = (Employee)httpSession.getAttribute("employee");
        map.put("list",claimVoucherService.getForSelf(employee.getSn()));
        return "claim_voucher_self";
    }

    //待处理报销单
    @RequestMapping("/deal")
    public String deal(HttpSession session, Map<String,Object> map){
        Employee employee = (Employee)session.getAttribute("employee");
        map.put("list",claimVoucherService.getForDeal(employee.getSn()));
        return "claim_voucher_deal";
    }

    //报销单修改
    @RequestMapping("/to_update")
    public String to_Update(int id,Map<String,Object> map){
        map.put("items",Contant.getItems());    //报销单信息
        ClaimVoucherInfo info = new ClaimVoucherInfo();
        info.setClaimVoucher(claimVoucherService.get(id));
        info.setItems(claimVoucherService.getclaimVoucherItems(id));
        map.put("info",info);
        return "claim_voucher_update";
    }
    @RequestMapping("/update")
    public String Update(HttpSession session, ClaimVoucherInfo Info){
        Employee employee = (Employee) session.getAttribute("employee");
        Info.getClaimVoucher().setCreateSn(employee.getSn());
        claimVoucherService.update(Info.getClaimVoucher(),Info.getItems());
        return "redirect:deal";
    }


    //报销单提交
    /*肯定要一个id*/
    @RequestMapping("/submit")
    public String submit(int id){
        claimVoucherService.submit(id);
        return "redirect:deal";
    }

    /*
    * 去处理
    * */
    @RequestMapping("/to_check")
    public String toCheck(int id,Map<String,Object>map){
        //报销单的详细信息
        map.put("claimVoucher",claimVoucherService.get(id));
        //报销单的附带信息
        map.put("items",claimVoucherService.getclaimVoucherItems(id));
        //报销单的日志信息
        map.put("records",claimVoucherService.getRecords(id));
        //jsp需要设置 modelAttribute
        //<form:form id="admin-form" name="addForm" action="/claim_voucher/check" modelAttribute="record">
        DealRecord dealRecord = new DealRecord();
        dealRecord.setClaimVoucherId(id);
        map.put("record",dealRecord);
        return "claim_voucher_check";
    }

    /*
    * 处理
    * */
    @RequestMapping("/check")
    //实际接收 肯定会接收到一个DealRecord对象
    public String Check(HttpSession session,DealRecord dealRecord){
        //拿出当前处理人
        Employee employee =(Employee) session.getAttribute("employee");
        //设置处理人
        dealRecord.setDealSn(employee.getSn());
        claimVoucherService.deal(dealRecord);
        return "redirect:deal"; //重定向待处理
    }
}
