package com.szj.service;

import com.szj.entity.Employee;

public interface GlobalService {
    Employee login(String sn,String password);
    void  changePassword(Employee employee);
}
