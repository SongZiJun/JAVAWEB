package com.szj.dao;

import com.szj.entity.Department;
import org.springframework.stereotype.Repository;

import javax.annotation.Resources;
import java.util.List;

@Repository("departmentDao")
public interface DepartmentDao {
    void insert(Department department);
    void update(Department department);
    void delete(String sn);
    Department select(String sn);
    List<Department> selectAll();
}
