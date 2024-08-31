package org.kwan.assignment.user.writer;

import org.kwan.assignment.user.model.Employee;

import java.util.List;

public interface EmployeeWriter {

    Employee crateEmployee(Employee employee);
    List<Employee> createEmployees(List<Employee> employees);
    Employee updateEmployee(Employee employee);
}
