package com.ust.employee_service.service;

import com.ust.employee_service.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee updateEmployee(Long id, Employee employee);
    void deleteEmployee(Long id);
    Employee getEmployeeById(Long id);
}
