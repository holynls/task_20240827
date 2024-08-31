package org.kwan.assignment.application.employee.usecase;

import org.kwan.assignment.user.model.Employee;

import java.util.List;

public interface GetEmployeeListUseCase {
    List<Employee> getEmployeeList(Integer page, Integer pageSize);

    List<Employee> getEmployeeByName(String name);
}
