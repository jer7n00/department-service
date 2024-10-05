package com.example.demo.service;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.entity.DepartmentEntity;
import com.example.demo.model.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceimp implements DepartmentService {
    DepartmentRepository deptRepo;

    @Autowired
    public DepartmentServiceimp(DepartmentRepository deptRepo){
        this.deptRepo=deptRepo;

    }

    @Override
    public List<Department> getAllDepartments() {
        List<DepartmentEntity> allDeptEntity= deptRepo.findAll();
        List<Department> allDept= new ArrayList<>();
        allDeptEntity.stream().forEach((eachDeptEntity)-> {
            Department dept = new Department();
            BeanUtils.copyProperties(eachDeptEntity, dept);
            allDept.add(dept);
        });
        return allDept;
    }

    @Override
    public Department getDepartment(long deptId) {
        Optional<DepartmentEntity> fetchedDeptEntity =deptRepo.findById(deptId);
        Department dept=null;
        if(fetchedDeptEntity.isPresent()){
            dept=new Department();
            BeanUtils.copyProperties(fetchedDeptEntity.get(),dept);

        }
        return dept;
    }

    @Override
    public Department addDepartment(Department newDepartment) {
        DepartmentEntity deptEntity = new DepartmentEntity();
        BeanUtils.copyProperties(newDepartment,deptEntity);
        deptRepo.saveAndFlush(deptEntity);
        return newDepartment;
    }

    @Override
    public Department updateDepartment(Department editDepartment) {
        DepartmentEntity deptEntity= new DepartmentEntity();
        BeanUtils.copyProperties(editDepartment,deptEntity);
        deptRepo.saveAndFlush(deptEntity);
        return editDepartment;
    }

    @Override
    public void deleteDepartment(long deptId) {
        deptRepo.deleteById(deptId);
    }




}
