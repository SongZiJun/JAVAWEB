package com.szj.service.impl;

import com.szj.dao.EmployeeDao;
import com.szj.entity.Employee;
import com.szj.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("globalServices")
public class GlobalServiceImpl implements GlobalService {
    @Autowired
    private EmployeeDao employeeDao;
    public Employee login(String sn, String password) {
        /*在数据库中查询是否有这个账号*/
        Employee employee = employeeDao.select(sn);
        /*如果有这个账号，并且employee中的密码和传递过来的密码相同*/
        if( employee!=null && employee.getPassword().equals(password)){
            return employee;
        }
        return null;
    }

    public void changePassword(Employee employee) {
        employeeDao.update(employee);
    }
}
