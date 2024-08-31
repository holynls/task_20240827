package org.kwan.javaspringstarter.user.writer;

import org.kwan.javaspringstarter.user.model.Employee;

import java.util.List;

public interface EmployeeWriter {

    Employee crateEmployee(Employee employee);
    List<Employee> createEmployees(List<Employee> employees);
    Employee updateEmployee(Employee employee);
}
