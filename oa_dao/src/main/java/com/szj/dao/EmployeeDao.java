package com.szj.dao;

import com.szj.entity.Department;
import com.szj.entity.Employee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("employeeDao")
public interface EmployeeDao {
    void insert(Employee employee);
    void update(Employee employee);
    void delete(String sn);
    Employee select(String sn);
    List<Employee> selectAll();

    //提交报销单
    //提供部门经理或者财务的人员获取 (编号 职位)
    //两个参数，要添加注解 方便映射文件的调用
    List<Employee> selectByDepartmentAndPost(@Param("dsn") String dsn,@Param("post") String post);
}
