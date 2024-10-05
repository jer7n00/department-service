package com.example.demo.controller;

import com.example.demo.model.Department;
import com.example.demo.model.Employee;
import com.example.demo.service.DepartmentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;


import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {
    DepartmentService deptService;



    public static final Logger LOG= LoggerFactory.getLogger(DepartmentController.class);
    @Autowired
    public DepartmentController(DepartmentService deptService){
        this.deptService=deptService;

    }


    @GetMapping("/departments")
    @CircuitBreaker(name="ciremp", fallbackMethod = "fallbackEmployee")
    public List<Department> getAllDepartments(){
        LOG.info("in getAllDepartments");
        List<Department> allDepts = deptService.getAllDepartments();
        allDepts.stream().forEach(dept -> {
            RestClient restClient = RestClient.create();
            List<Employee> allEmps=restClient
                    .get()
                    .uri("http://employee-sr:8082/api/employees/department/"+dept.getDeptId())
                    .retrieve()
                    .body(List.class);
            dept.setAllEmployees(allEmps);
        });
        return allDepts;


    }

    @GetMapping("/departments/{did}")
    @CircuitBreaker(name="ciremp", fallbackMethod = "fallbackEmployee")
    public Department getDepartment(@PathVariable("did") long deptId){
        LOG.info("in getDepartment");
        Department dept= deptService.getDepartment(deptId);
        RestClient restClient = RestClient.create();
        List<Employee> allEmps=restClient
                .get()
                .uri("http://employee-sr:8082/api/employees/department/"+deptId)
                .retrieve()
                .body(List.class);
        dept.setAllEmployees(allEmps);
        return dept;
    }

    @PostMapping("/departments")
    public Department addDepartment(@RequestBody Department newDept){
        LOG.info("in addDepartment");
        return deptService.addDepartment(newDept);

    }

    @PutMapping("/departments")
    public Department updateDepartment(@RequestBody Department editDept){
        LOG.info("in updateDepartment");
        return deptService.updateDepartment(editDept);
    }

    @DeleteMapping("/departments/{did}")
    public void removeDepartment(@PathVariable("did") long deptId){
        LOG.info("in removeDepartment");
        deptService.deleteDepartment(deptId);
    }

    public Department fallBackEmployee(){
        return new Department(0,"fallback",null);

    }
}
