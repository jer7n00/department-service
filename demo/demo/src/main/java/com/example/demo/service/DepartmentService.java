package com.example.demo.service;

import com.example.demo.entity.DepartmentEntity;
import com.example.demo.model.Department;
import java.util.List;

public interface DepartmentService  {
    List<Department> getAllDepartments();
    Department getDepartment(long deptId);
    Department addDepartment(Department newDepartment);
    Department updateDepartment(Department editDepartment);
    void deleteDepartment(long deptId);

}
